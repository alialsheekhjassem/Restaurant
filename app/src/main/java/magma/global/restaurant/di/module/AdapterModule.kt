package magma.global.restaurant.di.module

import dagger.Module
import dagger.Provides
import magma.global.restaurant.presentation.home.ui.home.RestaurantsAdapter


@Module
class AdapterModule {

    @Provides
    fun provideRestaurantsAdapter(): RestaurantsAdapter {
        return RestaurantsAdapter()
    }

    /*@Provides
    fun provideTrashAdapter(): TrashAdapter {
        return TrashAdapter()
    }*/

}