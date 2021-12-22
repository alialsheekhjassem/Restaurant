package magma.abikarshak.restaurant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import magma.abikarshak.restaurant.data.local.repository.dao.RestaurantDao
import magma.abikarshak.restaurant.model.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): RestaurantDao
}