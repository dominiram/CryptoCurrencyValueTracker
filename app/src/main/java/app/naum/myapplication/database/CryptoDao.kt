package app.naum.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: CryptoDatabaseModel): Long

    @Query("select * from crypto_models")
    suspend fun getAllCryptoModels(): List<CryptoDatabaseModel>

    @Query("select * from crypto_models where id = :id")
    suspend fun getCryptoModelById(id: String): CryptoDatabaseModel

    @Query("select * from crypto_models where symbol = :symbol")
    suspend fun getCryptoModelBySymbol(symbol: String): CryptoDatabaseModel
}