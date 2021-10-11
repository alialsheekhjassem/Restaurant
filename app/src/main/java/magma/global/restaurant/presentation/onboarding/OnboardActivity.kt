package magma.global.restaurant.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.AndroidInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.ActivityOnboardingBinding
import magma.global.restaurant.presentation.enter_code.EnterCodeActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    private lateinit var mViewPager: ViewPager2

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: OnboardViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OnboardViewModel::class.java)
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
        mViewPager.adapter = OnboardViewPagerAdapter(this, this)
        mViewPager.offscreenPageLimit = 1
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.txtSkip.text = null
                } else {
                    binding.txtSkip.text = getText(R.string.skip)
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })
        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()
    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }

    private fun goToEnterCodeActivity() {
        val intent = Intent(this@OnboardActivity, EnterCodeActivity::class.java)
        startActivity(intent)
    }
}