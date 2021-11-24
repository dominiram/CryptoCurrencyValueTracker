package app.naum.myapplication.repositories

import android.util.Log
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.network.APIService
import app.naum.myapplication.network.models.AllCoinsResponse
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.views.AllCoinsFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainRepo constructor(
    private val apiService: APIService
) {
    suspend fun getAllCoinsList(): Flow<DataState<List<CryptoModel>>> = flow {
        emit(DataState.Loading)
        try {
            val map = apiService.getAllCoinsList().data
            val values: Collection<CryptoModel> = map.values
            val array: ArrayList<CryptoModel> = ArrayList(values)
            val sortedArray = array.sortedWith(compareBy({it.sortOrder.length}, {it.sortOrder}))
            Log.d(TAG, "getAllCoinsList: data.size = ${values.size}")
            emit(DataState.Success(sortedArray))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getCoinOverview(symbol: String): Flow<DataState<CryptoModel>> = flow {
        emit(DataState.Loading)
        try {
            val coin = apiService.getCoinOverviewBySymbol(symbol)
            emit(DataState.Success(coin))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    companion object {
        val TAG = "MainRepo"
    }
}