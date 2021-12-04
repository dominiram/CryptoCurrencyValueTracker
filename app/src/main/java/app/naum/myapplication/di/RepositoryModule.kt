package app.naum.myapplication.di

import app.naum.myapplication.database.CryptoDatabase
import app.naum.myapplication.network.APIService
import app.naum.myapplication.repositories.MainRepo
import app.naum.myapplication.utils.NetworkToDatabaseCryptoModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepo(
        apiService: APIService,
        database: CryptoDatabase,
        mapper: NetworkToDatabaseCryptoModelMapper
    ): MainRepo {
        return MainRepo(
            apiService,
            database,
            mapper
        )
    }
}