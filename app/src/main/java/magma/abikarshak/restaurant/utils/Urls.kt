package magma.abikarshak.restaurant.utils

class Urls {

    companion object{
        const val NEWS = "/v2/everything/"
        const val NEARBY_PLACES = "/maps/api/place/nearbysearch/json"
        const val TEXT_SEARCH = "/maps/api/place/textsearch/json"

        const val END_POINT_LOGIN = "/api/auth/login"
        const val END_POINT_REGISTER = "/api/auth/register"
        const val END_POINT_RESET_PASSWORD = "/api/auth/resetPassword"
    }
}