package app.naum.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "crypto_models")
data class CryptoDatabaseModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "fullName")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "sortOrder")
    val sortOrder: String,
    @ColumnInfo(name = "isTrading")
    val isTrading: Boolean,
    @ColumnInfo(name = "totalCoinsMined")
    val totalMined: Double,
    @ColumnInfo(name = "assetWebsiteUrl")
    val coinWebsite: String,
    @ColumnInfo(name = "blockReward")
    val blockReward: Double,
    @ColumnInfo(name = "blockTime")
    val blockTime: Double
)
