package magma.global.restaurant.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import magma.global.restaurant.databinding.FragmentRestaurantDetailsBinding
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class RestaurantDetailsFragment : Fragment() {

    private var _binding: FragmentRestaurantDetailsBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RestaurantDetailsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(RestaurantDetailsViewModel::class.java)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
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