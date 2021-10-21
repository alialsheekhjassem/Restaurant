package magma.global.restaurant.presentation.details.address

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.FragmentAddressBinding
import magma.global.restaurant.utils.Const
import magma.global.restaurant.utils.TimePickerParentFragment
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class AddressFragment : Fragment(), View.OnClickListener,
    TimePickerDialog.OnTimeSetListener {

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

        setUpListeners()
        setUp()

        return binding.root
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener(this)
        binding.txtHour.setOnClickListener(this)
    }

    private fun setUp() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                navController.popBackStack()
            }
            R.id.txt_hour -> {
                showTimeDialog()
            }
        }
    }

    private fun showTimeDialog() {
        val datePicker: DialogFragment = TimePickerParentFragment()
        datePicker.setTargetFragment(this@AddressFragment, 0)
        datePicker.show(parentFragmentManager, Const.TAG_TimePickerParentFragment)
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val amPm = if (hourOfDay < 12) "AM" else "PM"
        binding.txtHour.text = hourOfDay.toString() + amPm
    }
}