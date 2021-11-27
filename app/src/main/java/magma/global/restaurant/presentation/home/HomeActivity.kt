package magma.global.restaurant.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.AndroidInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.ActivityHomeBinding
import magma.global.restaurant.utils.ViewModelFactory
import magma.global.restaurant.utils.meow_bottom_navigation.MeowBottomNavigation
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var meowBottomNavigation: MeowBottomNavigation

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_chats, R.id.navigation_my_order,
                R.id.navigation_notifications, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*val navView: BottomNavigationView = binding.navView

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_chats, R.id.navigation_my_order,
                R.id.navigation_notifications, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

        initTools()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        meowBottomNavigation.show(navController.currentDestination!!.id, false)
    }


    @SuppressLint("NonConstantResourceId")
    private fun initTools() {
        meowBottomNavigation = binding.navView

        meowBottomNavigation.apply {
            add(
                MeowBottomNavigation.Model(
                    R.id.navigation_home,
                    R.drawable.home
                )
            )
            add(
                MeowBottomNavigation.Model(
                    R.id.navigation_chats,
                    R.drawable.chat
                )
            )
            add(
                MeowBottomNavigation.Model(
                    R.id.navigation_my_order,
                    R.drawable.orders
                )
            )
            add(
                MeowBottomNavigation.Model(
                    R.id.navigation_notifications,
                    R.drawable.notifecation
                )
            )
            add(
                MeowBottomNavigation.Model(
                    R.id.navigation_profile,
                    R.drawable.profile
                )
            )
            setOnClickMenuListener { item ->
                Log.d("TAG", "initTools: $item")
                if (navController.currentDestination == null) return@setOnClickMenuListener
                when (item.id) {
                    R.id.navigation_home -> if (navController.currentDestination!!.id != R.id.navigation_home) {
                        navController.popBackStack()
                        navController.navigate(R.id.navigation_home)
                    }
                    R.id.navigation_chats -> if (navController.currentDestination!!.id != R.id.navigation_chats) {
                        navController.navigate(R.id.navigation_chats)
                    }
                    R.id.navigation_my_order -> if (navController.currentDestination!!.id != R.id.navigation_my_order) {
                        navController.navigate(R.id.navigation_my_order)
                    }
                    R.id.navigation_notifications -> if (navController.currentDestination!!.id != R.id.navigation_notifications) {
                        navController.navigate(R.id.navigation_notifications)
                    }
                    R.id.navigation_profile -> if (navController.currentDestination!!.id != R.id.navigation_profile) {
                        navController.navigate(R.id.navigation_profile)
                    }
                }
            }

            setOnShowListener { item ->
                Log.d("TAG", "AAALLLLLIIII initTools: ${item.id}")
            }
            setOnReselectListener { item ->
                Log.d("TAG", "AAALLLLLIIII initTools: ${item.id}")
            }
            show(R.id.navigation_home, false)
        }
    }
}