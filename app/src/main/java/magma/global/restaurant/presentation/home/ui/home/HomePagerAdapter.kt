package magma.global.restaurant.presentation.home.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import magma.global.restaurant.R

class HomePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return RestaurantsFragment.newInstance(position)
    }
}