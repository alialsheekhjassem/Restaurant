package magma.abikarshak.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.abikarshak.restaurant.presentation.details.FoodMenuFragment
import magma.abikarshak.restaurant.presentation.home.ui.chats.ChatsFragment
import magma.abikarshak.restaurant.presentation.details.RestaurantDetailsFragment
import magma.abikarshak.restaurant.presentation.details.address.AddressFragment
import magma.abikarshak.restaurant.presentation.details.cart.CartFragment
import magma.abikarshak.restaurant.presentation.home.ui.home.HomeFragment
import magma.abikarshak.restaurant.presentation.home.ui.home.RestaurantsFragment
import magma.abikarshak.restaurant.presentation.home.ui.my_order.MyOrderFragment
import magma.abikarshak.restaurant.presentation.home.ui.profile.ProfileFragment
import magma.abikarshak.restaurant.presentation.home.ui.notifications.NotificationsFragment
import magma.abikarshak.restaurant.presentation.onboarding.OnBoardingFragment
import magma.abikarshak.restaurant.presentation.registration.check_code.CheckCodeFragment
import magma.abikarshak.restaurant.presentation.registration.forget_password.ForgetPasswordFragment
import magma.abikarshak.restaurant.presentation.registration.language.LanguageFragment
import magma.abikarshak.restaurant.presentation.registration.login.LoginFragment
import magma.abikarshak.restaurant.presentation.registration.register.RegisterFragment
import magma.abikarshak.restaurant.presentation.registration.register_login.RegisterLoginFragment

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

    @ContributesAndroidInjector
    abstract fun contributeForgetPasswordFragment(): ForgetPasswordFragment


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