package magma.global.restaurant.data.repository

import magma.global.restaurant.data.remote.controller.Resource
import magma.global.restaurant.data.remote.responses.NearbySearchResponse
import magma.global.restaurant.model.Feed

interface DataSource {

    suspend fun getNearbyPlaces(type: String, location: String): Resource<NearbySearchResponse>
    suspend fun getPlaceDetailsByTitleAndLocation(query: String, location: String): Resource<NearbySearchResponse>
    fun updateNewsItem(deletedDate: Long,description: String)
    fun getDeletedNews(): List<Feed>
    fun deletePermanently(item : Feed)
    fun insertFeedList(feed : ArrayList<Feed>)
    fun insertFeedItem(feed : Feed)
}