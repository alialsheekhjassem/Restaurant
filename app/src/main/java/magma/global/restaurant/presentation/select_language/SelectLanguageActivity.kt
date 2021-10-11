package magma.global.restaurant.presentation.select_language

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivitySelectLanguageBinding
import magma.global.restaurant.presentation.select_city.SelectCityActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class SelectLanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectLanguageBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SelectLanguageViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SelectLanguageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.cardCountry.setOnClickListener{
            goToSelectCityActivity()
        }
    }

    private fun goToSelectCityActivity() {
        val intent = Intent(this@SelectLanguageActivity, SelectCityActivity::class.java)
        startActivity(intent)
    }
}