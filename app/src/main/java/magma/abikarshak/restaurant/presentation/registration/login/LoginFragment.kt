package magma.abikarshak.restaurant.presentation.registration.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.data.remote.controller.ErrorManager
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.databinding.FragmentLoginBinding
import magma.abikarshak.restaurant.presentation.home.HomeActivity
import magma.abikarshak.restaurant.utils.BindingUtils.hideKeyboard
import magma.abikarshak.restaurant.utils.Const
import magma.abikarshak.restaurant.utils.EventObserver
import magma.abikarshak.restaurant.utils.ViewModelFactory
import javax.inject.Inject
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import magma.abikarshak.restaurant.presentation.registration.forget_password.ForgetPasswordFragment
import magma.abikarshak.restaurant.utils.CommonUtils
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class LoginFragment : Fragment() {
    private val RC_SIGN_IN: Int = 100
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private var position = 0
    lateinit var binding: FragmentLoginBinding
    private lateinit var alertDialog: AlertDialog
    private var fcmToken: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setupObservers()

        return binding.root
    }

    private fun setUp() {
        binding.countryPicker.registerCarrierNumberEditText(binding.edtPhoneNumber)

        fetchFCMToken()
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

    private fun showLoadingDialog() {
        alertDialog = CommonUtils.showLoadingDialog(requireActivity())
    }

    private fun hideLoadingDialog() {
        alertDialog.cancel()
    }

    private fun setupObservers() {

        viewModel.actions.observe(
            viewLifecycleOwner, EventObserver(
                object : EventObserver.EventUnhandledContent<LoginActions> {
                    override fun onEventUnhandledContent(t: LoginActions) {
                        when (t) {
                            LoginActions.SIGN_UP_CLICKED -> {
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_login_to_register)
                            }
                            LoginActions.GOOGLE_CLICKED -> {
                                signIn()
                            }
                            LoginActions.FACEBOOK_CLICKED -> {
                                signIn()
                            }
                            LoginActions.FORGET_PASSWORD -> {
                                forgetPassword()
                            }
                            else -> {
                                Log.d(TAG, "onEventUnhandledContent: $t")
                            }
                        }
                    }
                })
        )
        // listen to api result
        viewModel.loginResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<ResponseWrapper<LoginResponse>>> {
                override fun onEventUnhandledContent(t: Resource<ResponseWrapper<LoginResponse>>) {
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
                            val loginResponse = response.successResult as LoginResponse
                            Log.d("TAG", "loginResponse: $loginResponse")

                            if (loginResponse.token != null) {
                                viewModel.saveToken(loginResponse)
                                goToHomeActivity()
                            }
                        }
                        is Resource.DataError -> {
                            hideLoadingDialog()
                            // usually this happening when there is server error
                            val response = t.response as ErrorManager
                            Log.d("TAG", "loginResponse: DataError $response")
                            if (GoogleSignIn.getLastSignedInAccount(requireActivity()) != null)
                                signOut()
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
                            Log.d("TAG", "onEventUnhandledContent: $response")
                            /*Toast.makeText(
                                requireContext(),
                                response.toString(),
                                Toast.LENGTH_SHORT
                            ).show()*/
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

        viewModel.loginValidation.observe(viewLifecycleOwner, { code ->
            val message: String = when (code) {
                Const.ERROR_EMPTY -> getString(R.string.field_can_not_be_empty)
                Const.ERROR_PHONE -> getString(R.string.phone_format_not_correct)
                else -> getString(R.string.password_format_not_correct)
            }
            //Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
            MotionToast.darkToast(
                requireActivity(),
                getString(R.string.error),
                message,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
            )
        })
    }

    private fun forgetPassword() {
        val forgetPasswordFragment: ForgetPasswordFragment = ForgetPasswordFragment.newInstance()
        forgetPasswordFragment.show(
            requireActivity().supportFragmentManager,
            Const.TAG_ForgetPasswordFragment
        )
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signOut: $it")
            }
        }
    }

    private fun goToHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun fetchFCMToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String> ->
                if (task.isSuccessful) {
                    fcmToken = task.result
                    binding.fcmToken = fcmToken
                }
            }
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
        ): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }

        private const val TAG = "LoginFragment"
    }
}