package magma.global.restaurant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import magma.global.restaurant.data.local.repository.dao.FeedDao
import magma.global.restaurant.model.Feed

@Database(
    entities = [Feed::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): FeedDao
}