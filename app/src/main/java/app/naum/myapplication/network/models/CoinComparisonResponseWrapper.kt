package app.naum.myapplication.network.models

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class CoinComparisonResponseWrapper(
    val data: CryptoComparisonModel
)
