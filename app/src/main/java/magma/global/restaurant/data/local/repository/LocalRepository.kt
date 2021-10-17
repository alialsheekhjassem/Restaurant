package magma.global.restaurant.data.local.repository

import magma.global.restaurant.data.local.repository.dao.RestaurantDao
import magma.global.restaurant.model.Restaurant
import javax.inject.Inject

class LocalRepository
@Inject constructor(
    private val restaurantDao: RestaurantDao
) {
    fun updateRestaurantItem(deletedDate: Long, description: String) {
        return restaurantDao.update(description, deletedDate)
    }

    fun insertRestaurantsList(restaurants: ArrayList<Restaurant>): LongArray {
        return restaurantDao.insertAll(restaurants)
    }

    fun insertRestaurantItem(restaurant: Restaurant) {
        return restaurantDao.insert(restaurant)
    }

    fun getDeletedRestaurant(): List<Restaurant> {
        return restaurantDao.getDeletedNews()
    }

    fun deletePermanently(item: Restaurant) {
        restaurantDao.deletePermanently(item)
    }

}