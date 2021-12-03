package app.naum.myapplication.di

import android.content.Context
import androidx.room.Room
import app.naum.myapplication.database.CryptoDao
import app.naum.myapplication.database.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideCryptoDatabase(
        @ApplicationContext context: Context
    ): CryptoDatabase {
        return Room
            .databaseBuilder(
                context,
                CryptoDatabase::class.java,
                CryptoDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCryptoDao(
        database: CryptoDatabase
    ): CryptoDao {
        return database.cryptoDao()
    }
}