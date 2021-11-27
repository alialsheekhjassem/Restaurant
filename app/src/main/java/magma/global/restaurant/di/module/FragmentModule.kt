package magma.global.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.global.restaurant.presentation.details.FoodMenuFragment
import magma.global.restaurant.presentation.home.ui.chats.ChatsFragment
import magma.global.restaurant.presentation.details.RestaurantDetailsFragment
import magma.global.restaurant.presentation.details.address.AddressFragment
import magma.global.restaurant.presentation.details.cart.CartFragment
import magma.global.restaurant.presentation.home.ui.home.HomeFragment
import magma.global.restaurant.presentation.home.ui.home.RestaurantsFragment
import magma.global.restaurant.presentation.home.ui.my_order.MyOrderFragment
import magma.global.restaurant.presentation.home.ui.profile.ProfileFragment
import magma.global.restaurant.presentation.home.ui.notifications.NotificationsFragment
import magma.global.restaurant.presentation.onboarding.OnBoardingFragment
import magma.global.restaurant.presentation.registration.check_code.CheckCodeFragment
import magma.global.restaurant.presentation.registration.language.LanguageFragment
import magma.global.restaurant.presentation.registration.login.LoginFragment
import magma.global.restaurant.presentation.registration.register.RegisterFragment
import magma.global.restaurant.presentation.registration.register_login.RegisterLoginFragment

@Module
internal abstract class FragmentModule {

    /*OnBoarding*/
    @ContributesAndroidInjector
    abstract fun contributeOnBoardingFragment(): OnBoardingFragment


    /*Account*/
    @ContributesAndroidInjector
    abstract fun contributeLanguageFragment(): LanguageFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterLoginFragment(): RegisterLoginFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributeCheckCodeFragment(): CheckCodeFragment


    /*Home*/
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): ChatsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @ContributesAndroidInjector
    abstract fun contributeMyOrderFragment(): MyOrderFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeRestaurantsFragment(): RestaurantsFragment

    @ContributesAndroidInjector
    abstract fun contributeRestaurantDetailsFragment(): RestaurantDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeFoodMenuFragment(): FoodMenuFragment

    @ContributesAndroidInjector
    abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun contributeAddressFragment(): AddressFragment

}