package magma.global.restaurant.di.module

import androidx.lifecycle.ViewModel
import magma.global.restaurant.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import magma.global.restaurant.presentation.enter_code.EnterCodeViewModel
import magma.global.restaurant.presentation.login.LoginViewModel
import magma.global.restaurant.presentation.onboarding.OnboardViewModel
import magma.global.restaurant.presentation.splash.SplashViewModel
import magma.global.restaurant.presentation.select_city.SelectCityViewModel
import magma.global.restaurant.presentation.select_region.SelectRegionViewModel
import magma.global.restaurant.presentation.select_language.SelectLanguageViewModel

// Because of @Binds, ViewModelModule needs to be an abstract class

@Module
abstract class ViewModelModule {

// Use @Binds to tell Dagger which implementation it needs to use when providing an interface.
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindMainViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectLanguageViewModel::class)
    abstract fun bindSelectLanguageViewModel(viewModel: SelectLanguageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectCityViewModel::class)
    abstract fun bindSelectCityViewModel(viewModel: SelectCityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectRegionViewModel::class)
    abstract fun bindSelectRegionViewModel(viewModel: SelectRegionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnterCodeViewModel::class)
    abstract fun bindEnterCodeViewModel(viewModel: EnterCodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnboardViewModel::class)
    abstract fun bindOnboardViewModel(viewModel: OnboardViewModel): ViewModel
    

}