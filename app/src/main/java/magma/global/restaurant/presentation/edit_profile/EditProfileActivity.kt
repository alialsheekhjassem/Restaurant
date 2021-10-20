package magma.global.restaurant.presentation.edit_profile

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.ActivityEditProfileBinding
import magma.global.restaurant.presentation.home.ui.profile.ProfileViewModel
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpAutoCompleteText()
        setUpListeners()

    }

    private fun setUpAutoCompleteText() {
        val sexArray = resources.getStringArray(R.array.sex)
        val autoCompleteText: AutoCompleteTextView = binding.spinnerSex
        val adapter = ArrayAdapter<CharSequence>(
            this,
            getAdapterItemLayout(), sexArray
        )
        autoCompleteText.setText(sexArray[0])
        autoCompleteText.setAdapter(adapter)
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener(this)
        binding.btnCamera.setOnClickListener(this)
        binding.txtBirthdateDay.setOnClickListener(this)
        binding.txtBirthdateMonth.setOnClickListener(this)
        binding.txtBirthdateYear.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
    }

    private fun getAdapterItemLayout(): Int {
        return R.layout.support_simple_spinner_dropdown_item
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                finish()
            }
            R.id.btn_camera -> {

            }
            R.id.txt_birthdate_day -> {

            }
            R.id.txt_birthdate_month -> {

            }
            R.id.txt_birthdate_year -> {

            }
            R.id.btn_save -> {

            }
            R.id.btn_cancel -> {
                finish()
            }
        }
    }

}