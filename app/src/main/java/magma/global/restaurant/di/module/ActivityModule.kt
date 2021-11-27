package magma.global.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.global.restaurant.presentation.details.DetailsActivity
import magma.global.restaurant.presentation.edit_profile.EditProfileActivity
import magma.global.restaurant.presentation.home.HomeActivity
import magma.global.restaurant.presentation.onboarding.OnBoardingActivity
import magma.global.restaurant.presentation.registration.RegistrationActivity

@Module
abstract class ActivityModule {

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