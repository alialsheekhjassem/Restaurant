package magma.global.restaurant.data.repository

import magma.global.restaurant.data.remote.controller.Resource
import magma.global.restaurant.data.remote.responses.NearbySearchResponse
import magma.global.restaurant.model.Restaurant

interface DataSource {

    suspend fun getNearbyPlaces(type: String, location: String): Resource<NearbySearchResponse>
    suspend fun getPlaceDetailsByTitleAndLocation(query: String, location: String): Resource<NearbySearchResponse>
    fun updateRestaurantItem(deletedDate: Long,description: String)
    fun getDeletedRestaurants(): List<Restaurant>
    fun deletePermanently(item : Restaurant)
    fun insertRestaurantList(restaurant : ArrayList<Restaurant>)
    fun insertRestaurantItem(restaurant : Restaurant)
}