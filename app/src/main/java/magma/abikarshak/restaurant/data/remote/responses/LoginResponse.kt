package magma.abikarshak.restaurant.data.remote.responses


data class LoginResponse(

    val _id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: String,
    val token: String?,
    val expireIn: Long

)

