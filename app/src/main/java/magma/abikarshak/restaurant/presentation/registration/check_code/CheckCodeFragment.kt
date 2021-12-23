package magma.abikarshak.restaurant.presentation.registration.check_code

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.data.remote.controller.ErrorManager
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.databinding.FragmentCheckCodeBinding
import magma.abikarshak.restaurant.utils.BindingUtils.hideKeyboard
import magma.abikarshak.restaurant.utils.CommonUtils
import magma.abikarshak.restaurant.utils.Const
import magma.abikarshak.restaurant.utils.EventObserver
import magma.abikarshak.restaurant.utils.ViewModelFactory
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CheckCodeFragment : Fragment() {
    lateinit var binding: FragmentCheckCodeBinding
    private var registerRequest: RegisterRequest? = null
    private lateinit var alertDialog: AlertDialog
    private var mAuth: FirebaseAuth? = null
    private var mCallbacks: OnVerificationStateChangedCallbacks? = null
    private var countDownTimer: CountDownTimer? = null
    private var mResendToken: ForceResendingToken? = null
    private lateinit var mVerificationId: String

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CheckCodeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CheckCodeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            registerRequest = requireArguments().getParcelable(Const.EXTRA_REGISTER_REQUEST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckCodeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        mAuth = FirebaseAuth.getInstance()
        startPhoneCallbacks()
        if (registerRequest?.phone != null)
            startPhoneNumberVerification(registerRequest!!.phone!!)

        setObservers()

        return binding.root
    }

    private fun setObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<CheckCodeActions> {
                    override fun onEventUnhandledContent(t: CheckCodeActions) {
                        when (t) {
                            CheckCodeActions.VERIFY_CLICKED -> {
                                verifyCode()
                            }
                            CheckCodeActions.EDIT_PHONE_CLICKED -> {
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_check_code_to_register)
                            }
                            CheckCodeActions.RESEND_CODE_CLICKED -> {
                                resendCode()
                            }
                        }
                    }
                })
        )


        // listen to api result
        viewModel.registerResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<ResponseWrapper<String>>> {
                override fun onEventUnhandledContent(t: Resource<ResponseWrapper<String>>) {
                    hideKeyboard()
                    when (t) {
                        is Resource.Loading -> {
                            // show progress bar and remove no data layout while loading
                            showLoadingDialog()
                        }
                        is Resource.Success -> {
                            // response is ok get the data and display it in the list
                            hideLoadingDialog()
                            val response = t.response as ResponseWrapper<*>
                            val registerResponse = response.successResult as String
                            Log.d(TAG, "registerResponse: $registerResponse")
                            MotionToast.darkToast(
                                requireActivity(),
                                getString(R.string.success),
                                getString(R.string.success),
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                            )

                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_check_code_to_login)
                        }
                        is Resource.DataError -> {
                            hideLoadingDialog()
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            Log.d(TAG, "registerResponse: DataError $response")
                            //signOut()
                            MotionToast.darkToast(
                                requireActivity(),
                                getString(R.string.error),
                                response.failureMessage,
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                            )
                        }
                        is Resource.Exception -> {
                            hideLoadingDialog()
                            // usually this happening when there is no internet
                            val response = t.response
                            Log.d(TAG, "onEventUnhandledContent: $response")
                            MotionToast.darkToast(
                                requireActivity(),
                                getString(R.string.error),
                                response.toString(),
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                            )
                        }
                    }
                }
            })
        )
    }

    private fun resendCode() {
        binding.txtResendCode.isEnabled = false
        binding.txtResendCode.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey_60))
        startTimer()
        if (registerRequest?.phone != null)
            mResendToken?.let { resendVerificationCode(registerRequest?.phone!!, it) }
    }

    private fun verifyCode() {
        hideKeyboard()
        if (binding.otpNumber.text != null && binding.otpNumber.text.toString().isNotEmpty()
            && binding.otpNumber.text.toString().length == 6
        ) {
            try {
                verifyPhoneNumberWithCode(mVerificationId, binding.otpNumber.text.toString())
            } catch (exception: Exception) {
                Log.d(TAG, "onCreateView: $exception")
            }
        } else {
            showErrorToast(getString(R.string.wrong_code))
        }
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: ForceResendingToken
    ) {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks!!) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(token) // ForceResendingToken from callbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun startPhoneCallbacks() {
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted: $credential")
                signInWithPhoneAuthCredential(credential)
            }

            @SuppressLint("SetTextI18n")
            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.d(TAG, "onVerificationFailed: $e")
                if (e is FirebaseAuthInvalidCredentialsException && context != null) {
                    // Invalid request
                    showErrorToast(getString(R.string.wrong_number) + " : " + e.message)
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    showErrorToast(e.message!!)
                } else {
                    if (e.message != null && e.message!!.contains("Error 403"))
                        showErrorToast(getString(R.string.forbidden))
                    else {
                        showErrorToast(getString(R.string.forbidden))
                    }
                }
                // Show a message and update the UI
                //alertDialog.dismiss()
            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent: $token")
                countDownTimer?.cancel()

                binding.btnConfirm.isEnabled = true
                startTimer()

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
                MotionToast.darkToast(
                    requireActivity(),
                    getString(R.string.success),
                    getString(R.string.code_sent),
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                )
            }
        }
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.txtResendCode.text = getString(R.string.resend_45s) + "(" +
                        (millisUntilFinished / 1000).toString() + ")"
            }

            override fun onFinish() {
                binding.txtResendCode.text = getString(R.string.resend_45s)
                binding.txtResendCode.isEnabled = true
                binding.txtResendCode.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary))
            }
        }.start()
    }

    private fun showErrorToast(errorMessage: String) {
        MotionToast.darkToast(
            requireActivity(),
            getString(R.string.error),
            errorMessage,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
        )
    }

    /*private fun goToHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential)
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options = mAuth?.let {
            mCallbacks?.let { it1 ->
                PhoneAuthOptions.newBuilder(it)
                    .setPhoneNumber(phoneNumber) // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(requireActivity()) // Activity (for callback binding)
                    .setCallbacks(it1) // OnVerificationStateChangedCallbacks
                    .build()
            }
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        // [END start_phone_auth]
    }

    // [END resend_verification]
    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                requireActivity()
            ) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = task.result.user
                    if (user != null) {
                        val idToken = user.getIdToken(false).result.token
                        val userUID = user.uid
                        doServerRegister(idToken, userUID)
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        MotionToast.darkToast(
                            requireActivity(),
                            getString(R.string.error),
                            getString(R.string.wrong_code),
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                        )
                        alertDialog.dismiss()
                    }
                }
            }
    }

    private fun doServerRegister(idToken: String?, userUID: String) {
        registerRequest?.firebaseAuthToken = idToken
        registerRequest?.let { viewModel.doServerRegister(it, idToken, userUID) }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun showLoadingDialog() {
        alertDialog = CommonUtils.showLoadingDialog(requireActivity())
    }

    private fun hideLoadingDialog() {
        alertDialog.dismiss()
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            position: Int,
            title: String?,
            description: String?,
            imageResource: Int
        ): CheckCodeFragment {
            val fragment = CheckCodeFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "CheckCodeFragment"
    }
}