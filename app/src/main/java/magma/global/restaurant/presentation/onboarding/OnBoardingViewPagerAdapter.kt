package magma.global.restaurant.presentation.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import magma.global.restaurant.R

class OnBoardingViewPagerAdapter(fragmentActivity: FragmentActivity, private val context: Context) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingFragment.newInstance(
                0,
                context.resources.getString(R.string.select_your_address_to_receive_your_order),
                context.resources.getString(R.string.choose_the_right_kitchen_for_your_occasion_whether_it_is_a_restaurant_independent_chefs_or_mobile_carts),
                R.drawable.first_page_image
            )
            1 -> OnBoardingFragment.newInstance(
                1,
                context.resources.getString(R.string.select_your_address_to_receive_your_order),
                context.resources.getString(R.string.choose_the_right_kitchen_for_your_occasion_whether_it_is_a_restaurant_independent_chefs_or_mobile_carts),
                R.drawable.second_page_image
            )
            else -> OnBoardingFragment.newInstance(
                2,
                context.resources.getString(R.string.select_your_address_to_receive_your_order),
                context.resources.getString(R.string.choose_the_right_kitchen_for_your_occasion_whether_it_is_a_restaurant_independent_chefs_or_mobile_carts),
                R.drawable.third_page_image
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}