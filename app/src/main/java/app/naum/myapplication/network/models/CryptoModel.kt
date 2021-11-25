package app.naum.myapplication.network.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoModel(
    @SerializedName("Id")
    val id: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Symbol")
    val symbol: String,
    @SerializedName("FullName")
    val name: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("SortOrder")
    val sortOrder: String,
    @SerializedName("IsTrading")
    val isTrading: Boolean,
    @SerializedName("TotalCoinsMined")
    val totalMined: Double,
    @SerializedName("AssetWebsiteUrl")
    val coinWebsite: String,
    @SerializedName("BlockReward")
    val blockReward: Double,
    @SerializedName("BlockTime")
    val blockTime: Double
): Parcelable
