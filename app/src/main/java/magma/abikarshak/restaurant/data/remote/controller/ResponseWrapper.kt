package magma.abikarshak.restaurant.data.remote.controller

data class ResponseWrapper<T>(val status : Int, val failureMessage:String, val successResult: T)