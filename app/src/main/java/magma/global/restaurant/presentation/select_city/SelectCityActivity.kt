package magma.global.restaurant.presentation.select_city

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivitySelectCityBinding
import magma.global.restaurant.presentation.select_region.SelectRegionActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class SelectCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectCityBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SelectCityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SelectCityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySelectCityBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.txtTitle.setOnClickListener{
            goToSelectRegionActivity()
        }
    }

    private fun goToSelectRegionActivity() {
        val intent = Intent(this@SelectCityActivity, SelectRegionActivity::class.java)
        startActivity(intent)
    }
}