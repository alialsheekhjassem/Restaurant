package magma.abikarshak.restaurant.di.module

import dagger.Module
import dagger.Provides
import magma.abikarshak.restaurant.presentation.details.FoodAdapter
import magma.abikarshak.restaurant.presentation.details.cart.CartAdapter
import magma.abikarshak.restaurant.presentation.home.ui.home.AdapterSliderView
import magma.abikarshak.restaurant.presentation.home.ui.home.FoodTypeAdapter
import magma.abikarshak.restaurant.presentation.home.ui.home.FoodsAdapter
import magma.abikarshak.restaurant.presentation.home.ui.home.RestaurantsAdapter
import magma.abikarshak.restaurant.presentation.home.ui.my_order.OrdersAdapter


@Module
class AdapterModule {

    @Provides
    fun provideAdapterSliderView(): AdapterSliderView {
        return AdapterSliderView()
    }

    @Provides
    fun provideRestaurantsAdapter(): RestaurantsAdapter {
        return RestaurantsAdapter()
    }

    @Provides
    fun provideFoodAdapter(): FoodsAdapter {
        return FoodsAdapter()
    }

    @Provides
    fun provideFoodTypeAdapter(): FoodTypeAdapter {
        return FoodTypeAdapter()
    }

    @Provides
    fun provideFoodsAdapter(): FoodAdapter {
        return FoodAdapter()
    }

    @Provides
    fun provideCartAdapter(): CartAdapter {
        return CartAdapter()
    }

    @Provides
    fun provideOrdersAdapter(): OrdersAdapter {
        return OrdersAdapter()
    }

}