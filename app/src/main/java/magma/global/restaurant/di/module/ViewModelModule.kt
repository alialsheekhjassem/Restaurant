package magma.global.restaurant.di.module

import androidx.lifecycle.ViewModel
import magma.global.restaurant.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import magma.global.restaurant.presentation.home.ui.dashboard.DashboardViewModel
import magma.global.restaurant.presentation.home.ui.home.HomeViewModel
import magma.global.restaurant.presentation.home.ui.my_order.MyOrderViewModel
import magma.global.restaurant.presentation.home.ui.profile.ProfileViewModel
import magma.global.restaurant.presentation.home.ui.notifications.NotificationsViewModel
import magma.global.restaurant.presentation.welcome.WelcomeViewModel

// Because of @Binds, ViewModelModule needs to be an abstract class

@Module
abstract class ViewModelModule {

// Use @Binds to tell Dagger which implementation it needs to use when providing an interface.

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

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
    

}