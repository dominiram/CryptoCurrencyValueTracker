package app.naum.myapplication.network.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import kotlin.collections.HashMap

data class AllCoinsResponse(
    val Response: String,
    val Message: String,
    @SerializedName("Data")
    val data: HashMap<String, CryptoModel>,
    val RateLimit: JsonObject,
    val HasWarning: Boolean,
    val Type: Int
//    val coinList: List<CryptoModel>
)
