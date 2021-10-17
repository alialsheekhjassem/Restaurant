package magma.global.restaurant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import magma.global.restaurant.data.local.repository.dao.RestaurantDao
import magma.global.restaurant.model.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): RestaurantDao
}