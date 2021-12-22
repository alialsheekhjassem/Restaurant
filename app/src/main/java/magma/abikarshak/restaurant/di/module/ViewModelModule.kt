package magma.abikarshak.restaurant.di.module

import androidx.lifecycle.ViewModel
import magma.abikarshak.restaurant.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import magma.abikarshak.restaurant.presentation.home.ui.chats.ChatsViewModel
import magma.abikarshak.restaurant.presentation.details.RestaurantDetailsViewModel
import magma.abikarshak.restaurant.presentation.details.address.AddressViewModel
import magma.abikarshak.restaurant.presentation.details.cart.CartViewModel
import magma.abikarshak.restaurant.presentation.home.ui.home.HomeViewModel
import magma.abikarshak.restaurant.presentation.home.ui.my_order.MyOrderViewModel
import magma.abikarshak.restaurant.presentation.home.ui.profile.ProfileViewModel
import magma.abikarshak.restaurant.presentation.home.ui.notifications.NotificationsViewModel
import magma.abikarshak.restaurant.presentation.onboarding.OnBoardingViewModel
import magma.abikarshak.restaurant.presentation.registration.check_code.CheckCodeViewModel
import magma.abikarshak.restaurant.presentation.registration.language.LanguageViewModel
import magma.abikarshak.restaurant.presentation.registration.login.LoginViewModel
import magma.abikarshak.restaurant.presentation.registration.register.RegisterViewModel
import magma.abikarshak.restaurant.presentation.splash.SplashViewModel

// Because of @Binds, ViewModelModule needs to be an abstract class

@Module
abstract class ViewModelModule {

// Use @Binds to tell Dagger which implementation it needs to use when providing an interface.

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnBoardingViewModel::class)
    abstract fun bindOnBoardingViewModel(viewModel: OnBoardingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LanguageViewModel::class)
    abstract fun bindLanguageViewModel(viewModel: LanguageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckCodeViewModel::class)
    abstract fun bindCheckCodeViewModel(viewModel: CheckCodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatsViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: ChatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    abstract fun bindNotificationsViewModel(viewModel: NotificationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyOrderViewModel::class)
    abstract fun bindMyOrderViewModel(viewModel: MyOrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantDetailsViewModel::class)
    abstract fun bindRestaurantDetailsViewModel(viewModel: RestaurantDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddressViewModel::class)
    abstract fun bindAddressViewModel(viewModel: AddressViewModel): ViewModel
    

}