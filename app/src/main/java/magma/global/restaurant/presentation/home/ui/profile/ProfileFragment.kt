package magma.global.restaurant.presentation.home.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.FragmentProfileBinding
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUpAutoCompleteText()

        return binding.root
    }

    private fun setUpAutoCompleteText() {
        val sexArray = resources.getStringArray(R.array.sex)
        val autoCompleteText: AutoCompleteTextView = binding.spinnerSex
        val adapter = ArrayAdapter<CharSequence>(
            requireActivity(),
            getAdapterItemLayout(), sexArray
        )
        autoCompleteText.setText(sexArray[0])
        autoCompleteText.setAdapter(adapter)
    }

    private fun getAdapterItemLayout(): Int {
        return R.layout.support_simple_spinner_dropdown_item
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