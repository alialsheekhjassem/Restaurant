package magma.global.restaurant.data.repository

import magma.global.restaurant.data.local.repository.LocalRepository
import magma.global.restaurant.data.remote.controller.Resource
import magma.global.restaurant.data.remote.responses.NearbySearchResponse
import magma.global.restaurant.model.Restaurant
import javax.inject.Inject

class DataRepository
@Inject
constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : DataSource {

    override suspend fun getNearbyPlaces(type: String, location: String): Resource<NearbySearchResponse> {
        return remoteRepository.getNearbyPlaces(type, location)
    }

    override suspend fun getPlaceDetailsByTitleAndLocation(query: String, location: String): Resource<NearbySearchResponse> {
        return remoteRepository.getPlaceDetailsByTitleAndLocation(query, location)
    }

    override fun updateRestaurantItem(deletedDate: Long,description: String) {
        localRepository.updateRestaurantItem(deletedDate,description)
    }

    override fun getDeletedRestaurants() : List<Restaurant>{
        return localRepository.getDeletedRestaurant()
    }

    override fun deletePermanently(item: Restaurant) {
        localRepository.deletePermanently(item)
    }

    override fun insertRestaurantList(restaurants: ArrayList<Restaurant>) {
        localRepository.insertRestaurantsList(restaurants)
    }

    override fun insertRestaurantItem(restaurant: Restaurant) {
        localRepository.insertRestaurantItem(restaurant)

    }

}

