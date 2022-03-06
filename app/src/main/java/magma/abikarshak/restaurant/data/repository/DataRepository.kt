package magma.abikarshak.restaurant.data.repository

import magma.abikarshak.restaurant.data.local.repository.LocalRepository
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.remote.requests.ResetPasswordRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.model.Restaurant
import javax.inject.Inject

class DataRepository
@Inject
constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : DataSource {

    //Api
    override suspend fun doServerLogin(loginRequest: LoginRequest): Resource<LoginResponse> {
        return remoteRepository.doServerLogin(loginRequest)
    }

    override suspend fun doServerRegister(registerRequest: RegisterRequest): Resource<ResponseWrapper<String>> {
        return remoteRepository.doServerRegister(registerRequest)
    }

    override suspend fun doServerResetPassword(request: ResetPasswordRequest): Resource<ResponseWrapper<String>> {
        return remoteRepository.doServerResetPassword(request)
    }

    //Local DB
    override fun updateRestaurantItem(deletedDate: Long,description: String) {
        localRepository.updateRestaurantItem(deletedDate,description)
    }

    override fun getDeletedRestaurants() : List<Restaurant>{
        return localRepository.getDeletedRestaurant()
    }

    override fun deletePermanently(item: Restaurant) {
        localRepository.deletePermanently(item)
    }

    override fun insertRestaurantList(restaurant: ArrayList<Restaurant>) {
        localRepository.insertRestaurantsList(restaurant)
    }

    override fun insertRestaurantItem(restaurant: Restaurant) {
        localRepository.insertRestaurantItem(restaurant)
    }


    //Pref
    override fun setApiToken(apiToken: String) {
        localRepository.setApiToken(apiToken)
    }

    override fun getApiToken(): String? {
        return localRepository.getApiToken()
    }

    override fun setLang(lang: String) {
        localRepository.setLang(lang)
    }

    override fun getLang(): String? {
        return localRepository.getLang()
    }

    override fun setIsShownOnBoarding(isShown: Boolean) {
        return localRepository.setIsShownOnBoarding(isShown)
    }

    override fun isShownOnBoarding(): Boolean {
        return localRepository.isShownOnBoarding()
    }

}

