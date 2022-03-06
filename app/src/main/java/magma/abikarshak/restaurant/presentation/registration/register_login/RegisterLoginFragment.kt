package magma.abikarshak.restaurant.presentation.registration.register_login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.data.remote.controller.ErrorManager
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.databinding.FragmentRegisterLoginBinding
import magma.abikarshak.restaurant.presentation.base.ProgressBarFragments
import magma.abikarshak.restaurant.presentation.registration.login.LoginViewModel
import magma.abikarshak.restaurant.utils.EventObserver
import magma.abikarshak.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class RegisterLoginFragment : ProgressBarFragments() {
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private var position = 0
    lateinit var binding: FragmentRegisterLoginBinding

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
        binding = FragmentRegisterLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_register_login_to_login)
        }

        binding.btnRegister.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_register_login_to_register)
        }
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {

        // listen to api result
        viewModel.loginResponse.observe(
            requireActivity(),
            EventObserver
                (object :
                EventObserver.EventUnhandledContent<Resource<LoginResponse>> {
                override fun onEventUnhandledContent(t: Resource<LoginResponse>) {
                    when (t) {
                        is Resource.Loading -> {
                            // show progress bar and remove no data layout while loading
                            showLoadingDialog()
                        }
                        is Resource.Success -> {
                            // response is ok get the data and display it in the list
                            hideLoadingDialog()
                            //val response = t.response as ResponseWrapper<*>
                            val loginResponse = t.response as LoginResponse
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
        ): RegisterLoginFragment {
            val fragment = RegisterLoginFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}