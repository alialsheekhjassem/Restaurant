package magma.global.restaurant.presentation.select_region

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivitySelectRegionBinding
import magma.global.restaurant.presentation.login.LoginActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class SelectRegionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRegionBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SelectRegionViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SelectRegionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySelectRegionBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.txtTitle.setOnClickListener{
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this@SelectRegionActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}