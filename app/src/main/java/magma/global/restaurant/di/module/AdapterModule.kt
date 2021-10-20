package magma.global.restaurant.di.module

import dagger.Module
import dagger.Provides
import magma.global.restaurant.presentation.details.FoodsAdapter
import magma.global.restaurant.presentation.details.cart.CartAdapter
import magma.global.restaurant.presentation.home.ui.home.RestaurantsAdapter


@Module
class AdapterModule {

    @Provides
    fun provideRestaurantsAdapter(): RestaurantsAdapter {
        return RestaurantsAdapter()
    }

    @Provides
    fun provideFoodsAdapter(): FoodsAdapter {
        return FoodsAdapter()
    }

    @Provides
    fun provideCartAdapter(): CartAdapter {
        return CartAdapter()
    }

    /*@Provides
    fun provideTrashAdapter(): TrashAdapter {
        return TrashAdapter()
    }*/

}