package magma.global.restaurant.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivityLoginBinding
import magma.global.restaurant.presentation.enter_code.EnterCodeActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        setUp()

        binding.btnLogin.setOnClickListener{
            goToEnterCodeActivity()
        }

    }

    private fun setUp() {
    }

    private fun goToEnterCodeActivity() {
        val intent = Intent(this@LoginActivity, EnterCodeActivity::class.java)
        startActivity(intent)
    }
}