package magma.abikarshak.restaurant.di.module

import magma.abikarshak.restaurant.data.repository.DataRepository
import magma.abikarshak.restaurant.data.repository.DataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataSource
}