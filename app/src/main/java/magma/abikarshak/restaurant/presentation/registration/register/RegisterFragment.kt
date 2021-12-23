package magma.abikarshak.restaurant.presentation.registration.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.FragmentRegisterBinding
import magma.abikarshak.restaurant.utils.*
import javax.inject.Inject

class RegisterFragment : Fragment() {
    private val RC_SIGN_IN: Int = 100
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private var position = 0
    lateinit var binding: FragmentRegisterBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = requireArguments().getInt(ARG_PARAM1)
            title = requireArguments().getString(ARG_PARAM2)!!
            description = requireArguments().getString(ARG_PARAM3)!!
            imageResource = requireArguments().getInt(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setUpObservers()

        return binding.root
    }

    private fun setUp() {
        binding.countryPicker.registerCarrierNumberEditText(binding.txtInputPhone)
        binding.stringRuleUtil = StringRuleUtil
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(binding.root.context)
        updateUI(account)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        Log.d(TAG, "updateUI: $account")
        if (account != null) {
            viewModel.doServerLogin(account)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun setUpObservers() {
        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<RegisterActions> {
                    override fun onEventUnhandledContent(t: RegisterActions) {
                        when (t) {
                            RegisterActions.SIGN_IN_CLICKED -> {
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_register_to_login)
                            }
                            RegisterActions.GOOGLE_CLICKED -> {
                                signIn()
                            }
                            RegisterActions.FACEBOOK_CLICKED -> {
                                signIn()
                            }
                        }
                    }
                })
        )

        viewModel.registerValidation.observe(viewLifecycleOwner, {
            Log.d(TAG, "RRR setUpObservers: $it")
            if (StringRuleUtil.NOT_EMPTY_RULE.validate(binding.txtInputName.text)) {
                binding.txtInputName.error = getString(R.string.name_can_not_be_empty)
            } else {
                binding.txtInputName.error = null
            }
            when {
                StringRuleUtil.NOT_EMPTY_RULE.validate(binding.txtInputPhone.text) -> {
                    binding.txtInputPhone.error = getString(R.string.field_can_not_be_empty)
                }
                StringRuleUtil.PHONE_RULE.validate(binding.txtInputPhone.text) -> {
                    binding.txtInputPhone.error = getString(R.string.phone_format_not_correct)
                }
                else -> {
                    binding.txtInputPhone.error = null
                }
            }
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
            if (!StringRuleUtil.NOT_EMPTY_RULE.validate(binding.txtInputEmail.text) &&
                StringRuleUtil.EMAIL_RULE.validate(binding.txtInputEmail.text)
            ) {
                binding.txtInputEmail.error = getString(R.string.please_enter_avalid_email)
            } else {
                binding.txtInputEmail.error = null
            }
        })
        // listen to api result
        /*viewModel.registerResponse.observe(
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
                                getString(R.string.error),
                                response.failureMessage,
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
                            )
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
        )*/
        viewModel.registerRequest.observe(viewLifecycleOwner, {
            Log.d(TAG, "RRR setUpObservers: " + it.name)
            val bundle = Bundle()
            bundle.putParcelable(Const.EXTRA_REGISTER_REQUEST, it)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_register_to_check_code, bundle)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
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
        ): RegisterFragment {
            val fragment = RegisterFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "RegisterFragment"
    }
}