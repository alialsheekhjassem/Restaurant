package magma.global.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.global.restaurant.presentation.home.ui.dashboard.DashboardFragment
import magma.global.restaurant.presentation.home.ui.home.HomeFragment
import magma.global.restaurant.presentation.home.ui.my_order.MyOrderFragment
import magma.global.restaurant.presentation.home.ui.profile.ProfileFragment
import magma.global.restaurant.presentation.home.ui.notifications.NotificationsFragment
import magma.global.restaurant.presentation.welcome.CheckCodeFragment
import magma.global.restaurant.presentation.welcome.LoginFragment
import magma.global.restaurant.presentation.welcome.WelcomeFragment

@Module
internal abstract class FragmentModule
{

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeCheckCodeFragment(): CheckCodeFragment

    //Home Activity
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @ContributesAndroidInjector
    abstract fun contributeMyOrderFragment(): MyOrderFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

}