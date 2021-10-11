package magma.global.restaurant.data.remote.controller

import magma.global.restaurant.data.remote.responses.NearbySearchResponse

internal interface IRestApiManager {

    suspend fun getNearbyPlaces(type: String, location: String): Resource<NearbySearchResponse>

    suspend fun getPlaceDetailsByTitleAndLocation(query: String, location: String): Resource<NearbySearchResponse>
}