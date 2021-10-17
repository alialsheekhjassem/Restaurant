package magma.global.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.global.restaurant.presentation.home.HomeActivity
import magma.global.restaurant.presentation.welcome.WelcomeActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeActivity(): WelcomeActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

}