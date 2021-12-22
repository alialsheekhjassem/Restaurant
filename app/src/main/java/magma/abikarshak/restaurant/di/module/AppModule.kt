package magma.abikarshak.restaurant.di.module

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import magma.abikarshak.restaurant.utils.Const
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import magma.abikarshak.restaurant.data.local.AppDatabase
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        Const.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(Const.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    fun provideGsonObject(): Gson {
        return Gson()
    }
}