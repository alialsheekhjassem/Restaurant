package magma.global.restaurant.presentation.enter_code

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivityEnterCodeBinding
import magma.global.restaurant.presentation.onboarding.OnboardActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class EnterCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterCodeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EnterCodeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(EnterCodeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityEnterCodeBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.edtNumber.setOtpCompletionListener { opt ->
            Log.d("TAG", "onCreate: $opt")
        }

        binding.btnLogin.setOnClickListener{
            goToSelectOnboardActivity()
        }
    }

    private fun goToSelectOnboardActivity() {
        val intent = Intent(this@EnterCodeActivity, OnboardActivity::class.java)
        startActivity(intent)
    }

    private fun openKeyboards(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.root, 0)
    }
}