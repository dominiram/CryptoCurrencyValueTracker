package app.naum.myapplication.di

import app.naum.myapplication.network.APIService
import app.naum.myapplication.repositories.MainRepo
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
        apiService: APIService
    ): MainRepo {
        return MainRepo(apiService)
    }
}