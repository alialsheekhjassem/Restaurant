package magma.global.restaurant.presentation.details.address

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
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

        return binding.root
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener(this)
        binding.txtHour.setOnClickListener(this)
        binding.btnContinue.setOnClickListener(this)
        binding.txtTitleOnline.setOnClickListener(this)
        binding.txtTitlePayOnDelivery.setOnClickListener(this)
        binding.txtTitleShareBill.setOnClickListener(this)
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
                backToPreviousStep()
            }
            R.id.txt_hour -> {
                showTimeDialog()
            }
            R.id.btn_continue -> {
                goToNextStep()
            }
            /*R.id.txt_title_online -> {
                if (binding.expandableLyt.isExpanded)
                    binding.expandableLyt.collapse()
                else binding.expandableLyt.expand()
            }
            R.id.txt_title_pay_on_delivery -> {
                if (binding.expandableLytPay.isExpanded)
                    binding.expandableLytPay.collapse()
                else binding.expandableLytPay.expand()
            }
            R.id.txt_title_share_bill -> {
                if (binding.expandableLytShare.isExpanded)
                    binding.expandableLytShare.collapse()
                else binding.expandableLytShare.expand()
            }*/
        }
    }

    private fun backToPreviousStep() {
        if (binding.expandableLyt.isExpanded && !binding.expandableLytPay.isExpanded && !binding.expandableLytShare.isExpanded) {
            binding.btnBack.isEnabled = false
            binding.expandableLyt.collapse()
        } else if (binding.expandableLytPay.isExpanded && !binding.expandableLytShare.isExpanded) {
            binding.expandableLyt.expand()
            binding.expandableLytPay.collapse()
        } else if (binding.expandableLytShare.isExpanded) {
            binding.expandableLytPay.expand()
            binding.expandableLytShare.collapse()
        }
    }

    private fun goToNextStep() {
        if (!binding.expandableLyt.isExpanded && !binding.expandableLytPay.isExpanded && !binding.expandableLytShare.isExpanded) {
            binding.btnBack.isEnabled = true
            binding.expandableLyt.expand()
        } else if (!binding.expandableLytPay.isExpanded && !binding.expandableLytShare.isExpanded) {
            binding.btnBack.isEnabled = true
            binding.expandableLyt.collapse()
            binding.expandableLytPay.expand()
        } else if (!binding.expandableLytShare.isExpanded) {
            binding.btnBack.isEnabled = true
            binding.expandableLyt.collapse()
            binding.expandableLytPay.collapse()
            binding.expandableLytShare.expand()
        } else {
            binding.btnBack.isEnabled = false
            binding.expandableLytShare.collapse()
            Toast.makeText(binding.root.context, getString(R.string.confirm), Toast.LENGTH_SHORT)
                .show()
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