package magma.global.restaurant.di.module

import magma.global.restaurant.data.repository.DataRepository
import magma.global.restaurant.data.repository.DataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataSource
}