package magma.abikarshak.restaurant.presentation.registration.check_code

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.databinding.FragmentCheckCodeBinding
import magma.abikarshak.restaurant.presentation.home.HomeActivity
import magma.abikarshak.restaurant.utils.BindingUtils.hideKeyboard
import magma.abikarshak.restaurant.utils.CommonUtils
import magma.abikarshak.restaurant.utils.Const
import magma.abikarshak.restaurant.utils.ViewModelFactory
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CheckCodeFragment : Fragment() {
    lateinit var binding: FragmentCheckCodeBinding
    private lateinit var registerRequest: RegisterRequest
    private lateinit var alertDialog: AlertDialog
    private var mAuth: FirebaseAuth? = null
    private var mCallbacks: OnVerificationStateChangedCallbacks? = null
    private lateinit var countDownTimer: CountDownTimer
    private var fcmToken: String? = null
    private var mResendToken: ForceResendingToken? = null
    private var mVerificationId: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CheckCodeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CheckCodeViewModel::class.java)
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
        //[START THE PROCESS OF VERIFICATION]
        fetchFCMToken()
        if (registerRequest.phone != null)
            startPhoneNumberVerification(registerRequest.phone!!)

        binding.btnConfirm.setOnClickListener {
            goToHomeActivity()
        }
        binding.btnEditPhoneNumber.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        return binding.root
    }

    private fun fetchFCMToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String> ->
                if (task.isSuccessful) {
                    fcmToken = task.result
                }
            }
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
                startTimer()

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
                Toast.makeText(
                    binding.root.context,
                    getString(R.string.code_sent),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startTimer() {
        countDownTimer.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtSubTitle.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
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

    private fun goToHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
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
                        //sendToServerN(idToken, userUID)
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

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun showLoadingDialog() {
        alertDialog = CommonUtils.showLoadingDialog(requireActivity())
    }

    private fun hideLoadingDialog() {
        alertDialog.cancel()
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