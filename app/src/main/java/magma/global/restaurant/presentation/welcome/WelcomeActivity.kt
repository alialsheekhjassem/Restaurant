package magma.global.restaurant.presentation.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivityWelcomeBinding
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    private lateinit var mViewPager: ViewPager2

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: WelcomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        setUp()

    }

    private fun setUp() {
        mViewPager = binding.viewPager
        mViewPager.adapter = WelcomeViewPagerAdapter(this, this)
        mViewPager.offscreenPageLimit = 1
        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()
    }

    /*private fun getItem(): Int {
        return mViewPager.currentItem
    }*/
}