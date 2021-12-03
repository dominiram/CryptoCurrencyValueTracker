package app.naum.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CryptoDatabaseModel::class],
    version = 1
)
abstract class CryptoDatabase: RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

    companion object {
        val DATABASE_NAME = "crypto_database"
    }
}