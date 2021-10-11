package magma.global.restaurant.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivitySplashBinding
import magma.global.restaurant.presentation.select_language.SelectLanguageActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        val timer = object: CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                goToMapsActivity()
            }
        }
        timer.start()

    }

    private fun goToMapsActivity() {
        val intent = Intent(this@SplashActivity, SelectLanguageActivity::class.java)
        startActivity(intent)
    }

}