package magma.global.restaurant.presentation.registration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.AndroidInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.ActivityRegisterationBinding
import magma.global.restaurant.presentation.registration.login.LoginViewModel
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterationBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        setUp()

    }

    private fun setUp() {
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_language, R.id.navigation_register_login, R.id.navigation_login,
                R.id.navigation_register_login
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /*private fun getItem(): Int {
        return mViewPager.currentItem
    }*/
}