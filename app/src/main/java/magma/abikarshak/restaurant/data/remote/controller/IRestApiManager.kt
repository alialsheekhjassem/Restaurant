package magma.abikarshak.restaurant.data.remote.controller

import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.remote.requests.ResetPasswordRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse

internal interface IRestApiManager {

    suspend fun doServerLogin(loginRequest: LoginRequest): Resource<ResponseWrapper<LoginResponse>>

    suspend fun doServerRegister(registerRequest: RegisterRequest): Resource<ResponseWrapper<String>>

    suspend fun doServerResetPassword(request: ResetPasswordRequest): Resource<ResponseWrapper<String>>
}