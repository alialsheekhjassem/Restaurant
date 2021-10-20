package magma.global.restaurant.presentation.details.address

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.FragmentAddressBinding
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private lateinit var navController: NavController

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: AddressViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AddressViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        navController = requireParentFragment().findNavController()

        setUp()

        return binding.root
    }

    private fun setUp() {
        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}