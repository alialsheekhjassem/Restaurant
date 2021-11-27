package magma.global.restaurant.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.AndroidInjection
import magma.global.restaurant.databinding.ActivityOnboardingBinding
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    private lateinit var mViewPager: ViewPager2

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: OnBoardingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OnBoardingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        supportActionBar?.hide()

        setUp()

    }

    private fun setUp() {
        mViewPager = binding.viewPager
        mViewPager.adapter = OnBoardingViewPagerAdapter(this, this)
        //mViewPager.offscreenPageLimit = 1
        //mViewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()
    }

    /*private fun getItem(): Int {
        return mViewPager.currentItem
    }*/
}