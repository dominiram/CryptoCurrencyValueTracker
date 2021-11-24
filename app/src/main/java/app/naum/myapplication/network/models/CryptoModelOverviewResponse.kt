package app.naum.myapplication.network.models

import com.google.gson.annotations.SerializedName

data class CryptoModelOverviewResponse(
    val Response: String,
    val Message: String,
    @SerializedName("Data")
    val data: HashMap<String, CryptoModel>
)
