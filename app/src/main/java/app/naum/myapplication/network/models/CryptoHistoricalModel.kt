package app.naum.myapplication.network.models

import com.google.gson.annotations.SerializedName

data class CryptoHistoricalWrapper(
    val Response: String,
    val Message: String,
    @SerializedName("Data")
    val data: CryptoHistoricalModel
)

data class CryptoHistoricalModel(
    val TimeFrom: Int,
    val TimeTo: Int,
    @SerializedName("Data")
    val data: List<HistoricalCryptoData>
)

data class HistoricalCryptoData(
    val time: Int,
    val high: Double,
    val low: Double,
    val open: Double,
    val close: Double
)
