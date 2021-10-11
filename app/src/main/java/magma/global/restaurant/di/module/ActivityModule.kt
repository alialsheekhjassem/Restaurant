package magma.global.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import magma.global.restaurant.presentation.enter_code.EnterCodeActivity
import magma.global.restaurant.presentation.login.LoginActivity
import magma.global.restaurant.presentation.onboarding.OnboardActivity
import magma.global.restaurant.presentation.splash.SplashActivity
import magma.global.restaurant.presentation.select_city.SelectCityActivity
import magma.global.restaurant.presentation.select_region.SelectRegionActivity
import magma.global.restaurant.presentation.select_language.SelectLanguageActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeSelectLanguageActivity(): SelectLanguageActivity

    @ContributesAndroidInjector
    abstract fun contributeSelectCityActivity(): SelectCityActivity

    @ContributesAndroidInjector
    abstract fun contributeSelectRegionActivity(): SelectRegionActivity

    @ContributesAndroidInjector
    abstract fun contributeEnterCodeActivity(): EnterCodeActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeOnboardActivity(): OnboardActivity

}