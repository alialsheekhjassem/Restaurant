package magma.abikarshak.restaurant.data.local.repository.dao

import androidx.room.*
import magma.abikarshak.restaurant.model.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(restaurants: ArrayList<Restaurant>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: Restaurant)

    @Query("UPDATE Restaurant SET deleted =:deleted, deletedDate=:deletedDate  WHERE description =:description")
    fun update(description: String,deletedDate : Long, deleted: Boolean = true)

    @Query("SELECT * FROM Restaurant WHERE deleted =:deleted")
    fun getDeletedNews(deleted: Boolean = true): List<Restaurant>

    @Delete
    fun deletePermanently(item: Restaurant)

}