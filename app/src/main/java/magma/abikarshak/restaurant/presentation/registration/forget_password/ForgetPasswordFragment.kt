package magma.abikarshak.restaurant.presentation.registration.forget_password

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.data.remote.controller.ErrorManager
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.databinding.FragmentForgetPasswordBinding
import magma.abikarshak.restaurant.utils.*
import magma.abikarshak.restaurant.utils.BindingUtils.hideKeyboard
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForgetPasswordFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentForgetPasswordBinding
    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var alertDialog: AlertDialog
    private lateinit var mVerificationId: String
    private var mAuthToken: String? = null
    private lateinit var mUserUid: String

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ForgetPasswordViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ForgetPasswordViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setUpObservers()

        return binding.root
    }

    private fun setUp() {
        binding.countryPicker.registerCarrierNumberEditText(binding.edtPhoneNumber)
        binding.stringRuleUtil = StringRuleUtil

        mAuth = FirebaseAuth.getInstance()
        startPhoneCallbacks()

        nextLayout(binding.lytEditPhone, 1f, true)
        nextLayout(binding.lytVerificationCode, 0.5f, false)
        nextLayout(binding.lytResetPassword, 0.5f, false)
    }

    private fun setUpObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<ForgetPasswordActions> {
                    override fun onEventUnhandledContent(t: ForgetPasswordActions) {
                        when (t) {
                            ForgetPasswordActions.CONFIRM_CLICKED -> {
                                confirmPhone()
                            }
                            ForgetPasswordActions.VERIFY_CLICKED -> {
                                verifyCode()
                            }
                            ForgetPasswordActions.SAVE_NEW_PASSWORD_CLICKED -> {
                                doServerSaveNewPassword()
                            }
                            ForgetPasswordActions.CLOSE_CLICKED -> {
                                dismiss()
                            }
                        }
                    }
                })
        )

        viewModel.resetPasswordValidation.observe(viewLifecycleOwner, {
            Log.d(TAG, "RRR setUpObservers: $it")
            when {
                StringRuleUtil.NOT_EMPTY_RULE.validate(binding.txtInputPassword.text) -> {
                    binding.txtInputPassword.error =
                        getString(R.string.please_enter_avalid_password)
                }
                StringRuleUtil.PASSWORD_RULE.validate(binding.txtInputPassword.text) -> {
                    binding.txtInputPassword.error =
                        getString(R.string.please_enter_avalid_password)
                }
                else -> {
                    binding.txtInputPassword.error = null
                }
            }
            when {
                StringRuleUtil.PASSWORD_RULE.validate(binding.txtInputPassword.text) ||
                        binding.txtInputConfirmPassword.text.toString()
                        != binding.txtInputPassword.text.toString() -> {
                    binding.txtInputConfirmPassword.error =
                        getString(R.string.please_enter_avalid_confirm_password)
                }
                else -> {
                    binding.txtInputConfirmPassword.error = null
                }
            }
        })

        // listen to api result
        viewModel.resetPasswordResponse.observe(
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
                            val successResult = response.successResult as String
                            Log.d("TAG", "resetPasswordResponse: $successResult")
                            showSuccessToast(successResult)
                            dismiss()
                        }
                        is Resource.DataError -> {
                            hideLoadingDialog()
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            Log.d("TAG", "resetPasswordResponse: DataError $response")
                            showErrorToast(response.failureMessage)
                        }
                        is Resource.Exception -> {
                            hideLoadingDialog()
                            // usually this happening when there is no internet
                            val response = t.response
                            Log.d("TAG", "onEventUnhandledContent: $response")
                            showErrorToast(response.toString())
                        }
                    }
                }
            })
        )
    }

    private fun doServerSaveNewPassword() {
        val edtPassword = binding.txtInputPassword.editableText
        val edtConfirmPassword = binding.txtInputConfirmPassword.editableText
        val fullPhoneNumber = binding.countryPicker.fullNumberWithPlus
        mAuthToken?.let {
            viewModel.doServerSaveNewPassword(fullPhoneNumber, edtPassword, edtConfirmPassword, it)
        }
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

    private fun startPhoneCallbacks() {
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
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
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent: $token")
                binding.btnVerify.isEnabled = true

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId

                // Save verification ID and resending token so we can use them later
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
                        this.mAuthToken = idToken
                        this.mUserUid = userUID
                        nextLayout(binding.lytEditPhone, 0.5f, false)
                        nextLayout(binding.lytVerificationCode, 0.5f, false)
                        nextLayout(binding.lytResetPassword, 1f, true)
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
                        hideLoadingDialog()
                    }
                }
            }
    }

    private fun confirmPhone() {
        val fullPhoneNumber: String?
        val edtPhone: Editable = binding.edtPhoneNumber.text
        when {
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtPhone) -> {
                showErrorToast(getString(R.string.please_enter_avalid_phone))
                return
            }
            StringRuleUtil.PHONE_RULE.validate(edtPhone) -> {
                showErrorToast(getString(R.string.phone_format_not_correct))
                return
            }
            else -> {
                fullPhoneNumber = binding.countryPicker.fullNumberWithPlus
                startPhoneNumberVerification(fullPhoneNumber)
                nextLayout(binding.lytEditPhone, 0.5f, false)
                nextLayout(binding.lytVerificationCode, 1f, true)
                nextLayout(binding.lytResetPassword, 0.5f, false)
            }
        }
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

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun nextLayout(lytParent: LinearLayoutCompat, alpha: Float, enable: Boolean) {
        lytParent.alpha = alpha
        disableEnableControls(enable, lytParent)
    }

    private fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
        for (i in 0 until vg.childCount) {
            val child = vg.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
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

    private fun showSuccessToast(errorMessage: String) {
        MotionToast.darkToast(
            requireActivity(),
            getString(R.string.success),
            errorMessage,
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
        )
    }

    private fun showLoadingDialog() {
        alertDialog = CommonUtils.showLoadingDialog(requireActivity())
    }

    private fun hideLoadingDialog() {
        alertDialog.dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    companion object {
        fun newInstance(): ForgetPasswordFragment {
            return ForgetPasswordFragment()
        }

        private const val TAG = "ForgetPasswordFragment"
    }
}