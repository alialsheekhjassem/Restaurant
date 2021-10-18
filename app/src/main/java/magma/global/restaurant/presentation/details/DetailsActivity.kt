package magma.global.restaurant.presentation.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.ActivityDetailsBinding
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            finish()
        }

        navController = findNavController(R.id.nav_host_details)
    }
}