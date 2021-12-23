package magma.abikarshak.restaurant.data.remote.services

import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.remote.requests.ResetPasswordRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.data.remote.responses.NearbySearchResponse
import magma.abikarshak.restaurant.utils.Urls
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IFoodService {

    @GET(Urls.NEARBY_PLACES)
    suspend fun getNearbyPlaces(
        @Query("type") type: String?,
        @Query("location") location: String?,
        @Query("radius") radius: Int,
        @Query("key") apiToken: String?
    ): Response<NearbySearchResponse>

    @GET(Urls.TEXT_SEARCH)
    suspend fun getPlaceDetailsByTitleAndLocation(
        @Query("query") type: String?,
        @Query("location") location: String?,
        @Query("key") apiToken: String?
    ): Response<NearbySearchResponse>

    @POST(Urls.END_POINT_LOGIN)
    suspend fun doServerLogin(
        @Body loginRequest: LoginRequest?
    ): Response<ResponseWrapper<LoginResponse>>

    @POST(Urls.END_POINT_REGISTER)
    suspend fun doServerRegister(
        @Body registerRequest: RegisterRequest?
    ): Response<ResponseWrapper<String>>

    @POST(Urls.END_POINT_RESET_PASSWORD)
    suspend fun doServerResetPassword(
        @Body request: ResetPasswordRequest?
    ): Response<ResponseWrapper<String>>

}