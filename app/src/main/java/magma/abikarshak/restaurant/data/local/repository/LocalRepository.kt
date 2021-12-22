package magma.abikarshak.restaurant.data.local.repository

import android.content.SharedPreferences
import magma.abikarshak.restaurant.data.local.repository.dao.RestaurantDao
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.utils.Const
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LocalRepository
@Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val preferences: SharedPreferences
) {

    //Preferences
    fun setApiToken(apiToken: String) {
        preferences.edit().putString(Const.PREF_API_TOKEN, apiToken).apply()
    }

    fun getApiToken(): String? {
        return preferences.getString(Const.PREF_API_TOKEN, null)
    }

    fun setLang(lang: String) {
        preferences.edit().putString(Const.PREF_LANG, lang).apply()
    }

    fun getLang(): String? {
        return preferences.getString(Const.PREF_LANG, Locale.getDefault().displayLanguage)
    }

    fun setIsShownOnBoarding(isShown: Boolean) {
        preferences.edit().putBoolean(Const.PREF_IS_SHOWN_ONBOARDING, isShown).apply()
    }

    fun isShownOnBoarding(): Boolean {
        return preferences.getBoolean(Const.PREF_IS_SHOWN_ONBOARDING, false)
    }
    //End pref

    fun updateRestaurantItem(deletedDate: Long, description: String) {
        return restaurantDao.update(description, deletedDate)
    }

    fun insertRestaurantsList(restaurants: ArrayList<Restaurant>): LongArray {
        return restaurantDao.insertAll(restaurants)
    }

    fun insertRestaurantItem(restaurant: Restaurant) {
        return restaurantDao.insert(restaurant)
    }

    fun getDeletedRestaurant(): List<Restaurant> {
        return restaurantDao.getDeletedNews()
    }

    fun deletePermanently(item: Restaurant) {
        restaurantDao.deletePermanently(item)
    }

}