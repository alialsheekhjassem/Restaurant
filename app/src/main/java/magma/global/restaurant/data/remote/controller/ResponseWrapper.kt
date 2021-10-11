package magma.global.restaurant.data.remote.controller

@Suppress("unused")
data class ResponseWrapper<T>(val code : Int, val message:String, val data: T)