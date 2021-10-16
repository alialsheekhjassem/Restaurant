package magma.global.restaurant.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
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

    /*@ContributesAndroidInjector
    abstract fun contributeTrashFragment(): TrashFragment*/

}