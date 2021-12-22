package magma.abikarshak.restaurant.data.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import magma.abikarshak.restaurant.data.remote.controller.*
import magma.abikarshak.restaurant.data.remote.controller.IRestApiManager
import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.data.remote.services.IFoodService
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RemoteRepository
@Inject constructor(private val serviceGenerator: ServiceGenerator, private val gson: Gson) :
    IRestApiManager {

    override suspend fun doServerLogin(loginRequest: LoginRequest): Resource<ResponseWrapper<LoginResponse>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.doServerLogin(loginRequest)

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val loginResponse = response.body() as ResponseWrapper<LoginResponse>
                Log.d(TAG, "doServerLogin: isSuccessful " + response.code())
                Log.d(TAG, "doServerLogin: isSuccessful $loginResponse")
                Resource.Success(loginResponse)
            } else {
                Log.d(TAG, "doServerLogin: isSuccessful no " + response.code())
                Log.d(TAG, "doServerLogin: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    override suspend fun doServerRegister(registerRequest: RegisterRequest): Resource<ResponseWrapper<String>> {
        val authService = serviceGenerator.createService(IFoodService::class.java)
        try {
            val response = authService.doServerRegister(registerRequest)

            return if (response.isSuccessful) {
                //Do something with response e.g show to the UI.
                val registerResponse = response.body() as ResponseWrapper<String>
                Log.d(TAG, "doServerRegister: isSuccessful " + response.code())
                Log.d(TAG, "doServerRegister: isSuccessful $registerResponse")
                Resource.Success(registerResponse)
            } else {
                Log.d(TAG, "doServerRegister: isSuccessful no " + response.code())
                Log.d(TAG, "doServerRegister: isSuccessful no " + response.message())
                val errorBody = gson.fromJson(
                    response.errorBody()?.stringSuspending(),
                    ErrorManager::class.java
                )
                Resource.DataError(errorBody)
            }
        } catch (e: HttpException) {
            return Resource.Exception(e.message() as String)
        } catch (e: Throwable) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: SocketTimeoutException) {
            return Resource.Exception(errorMessage = e.message as String)
        } catch (e: IOException) {
            return Resource.Exception(errorMessage = e.message as String)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun ResponseBody.stringSuspending() =
        withContext(Dispatchers.IO) { string() }

    companion object {
        private const val TAG = "RemoteRepository"
    }

}