package magma.abikarshak.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.abikarshak.restaurant.presentation.details.DetailsActivity
import magma.abikarshak.restaurant.presentation.edit_profile.EditProfileActivity
import magma.abikarshak.restaurant.presentation.home.HomeActivity
import magma.abikarshak.restaurant.presentation.onboarding.OnBoardingActivity
import magma.abikarshak.restaurant.presentation.registration.RegistrationActivity
import magma.abikarshak.restaurant.presentation.splash.SplashActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeOnBoardingActivity(): OnBoardingActivity

    @ContributesAndroidInjector
    abstract fun contributeRegistrationActivity(): RegistrationActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector
    abstract fun contributeEditProfileActivity(): EditProfileActivity

}