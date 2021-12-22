package magma.abikarshak.restaurant.presentation.registration.language

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.FragmentLanguageBinding
import magma.abikarshak.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class LanguageFragment : Fragment() {
    lateinit var binding: FragmentLanguageBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LanguageViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LanguageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        binding.btnEnglish.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_languages_to_login_register)
        }
        binding.btnArabic.setOnClickListener {
            binding.btnEnglish.performClick()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (viewModel.isShowOnBoarding()) {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_languages_to_login_register)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}