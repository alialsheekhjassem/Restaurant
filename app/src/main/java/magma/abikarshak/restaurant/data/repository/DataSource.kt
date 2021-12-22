package magma.abikarshak.restaurant.data.repository

import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.model.Restaurant

interface DataSource {

    //Api
    suspend fun doServerLogin(loginRequest: LoginRequest): Resource<ResponseWrapper<LoginResponse>>
    suspend fun doServerRegister(registerRequest: RegisterRequest): Resource<ResponseWrapper<String>>

    //Local
    fun updateRestaurantItem(deletedDate: Long,description: String)
    fun getDeletedRestaurants(): List<Restaurant>
    fun deletePermanently(item : Restaurant)
    fun insertRestaurantList(restaurant : ArrayList<Restaurant>)
    fun insertRestaurantItem(restaurant : Restaurant)

    //Pref
    fun setApiToken(apiToken: String)
    fun getApiToken(): String?
    fun setLang(lang: String)
    fun getLang(): String?
    fun setIsShownOnBoarding(isShown: Boolean)
    fun isShownOnBoarding(): Boolean?
}