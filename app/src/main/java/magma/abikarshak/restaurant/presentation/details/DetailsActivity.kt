package magma.abikarshak.restaurant.presentation.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.android.AndroidInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.ActivityDetailsBinding
import magma.abikarshak.restaurant.utils.ViewModelFactory
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