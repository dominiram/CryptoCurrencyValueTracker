package app.naum.myapplication.repositories

import android.util.Log
import app.naum.myapplication.database.CryptoDatabase
import app.naum.myapplication.models.GraphCoordinatesModel
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.network.APIService
import app.naum.myapplication.network.models.HistoricalCryptoData
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.utils.NetworkToDatabaseCryptoModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepo constructor(
    private val apiService: APIService,
    private val database: CryptoDatabase,
    private val mapper: NetworkToDatabaseCryptoModelMapper
) {
    suspend fun getAllCoinsList(): Flow<DataState<List<CryptoModel>>> = flow {
        emit(DataState.Loading)
        try {
            val map = apiService.getAllCoinsList().data
            val values: Collection<CryptoModel> = map.values
            val array: ArrayList<CryptoModel> = ArrayList(values)
            val sortedArray = array.sortedWith(compareBy({ it.sortOrder.length }, { it.sortOrder }))
            sortedArray.forEach {
                database.cryptoDao().insert(mapper.mapToDatabaseModel(it))
            }
            Log.d(TAG, "getAllCoinsList: data.size = ${values.size}")
            emit(DataState.Success(sortedArray))

        } catch (e: Exception) {
            try {
                val list = database.cryptoDao().getAllCryptoModels()
                val cryptoModelList: MutableList<CryptoModel> = mutableListOf()
                list.forEach {
                    cryptoModelList.add(mapper.mapToNetworkAndApplicationModel(it))
                }
                emit(DataState.Success(cryptoModelList))
            } catch (ee: Exception) {
                emit(DataState.Error(ee))
            }
        }
    }

    suspend fun getHistoricalDataForDay(fromSymbol: String, numberOfDays: Int):
            Flow<DataState<GraphCoordinatesModel>> = flow {
        emit(DataState.Loading)
        try {
            val historicalList = apiService
                .getHistoricalDataForDay(
                    fromSymbol,
                    SYMBOL_TO_COMPARE,
                    numberOfDays
                )
            emit(DataState.Success(getGraphCoordinatesModel(historicalList.data.data)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getHistoricalDataForHour(fromSymbol: String, numberOfDays: Int):
            Flow<DataState<GraphCoordinatesModel>> = flow {
        emit(DataState.Loading)
        try {
            val historicalList = apiService
                .getHistoricalDataForHour(
                    fromSymbol,
                    SYMBOL_TO_COMPARE,
                    numberOfDays
                )
            emit(DataState.Success(getGraphCoordinatesModel(historicalList.data.data)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getHistoricalDataForMinute(fromSymbol: String, numberOfDays: Int):
            Flow<DataState<GraphCoordinatesModel>> = flow {
        emit(DataState.Loading)
        try {
            val historicalList = apiService
                .getHistoricalDataForMinute(
                    fromSymbol,
                    SYMBOL_TO_COMPARE,
                    numberOfDays
                ) //.data.data.onEach {  }
            emit(DataState.Success(getGraphCoordinatesModel(historicalList.data.data)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Helper methods

    private fun getGraphCoordinatesModel(data: List<HistoricalCryptoData>): GraphCoordinatesModel {
        val maxValue = data.maxOf { it.high }
        val minValue = data.minOf { it.low }
        val xCords: MutableList<Int> = mutableListOf()
        val yCords: MutableList<Double> = mutableListOf()
        for (i in data.indices) {
            xCords.add(data[i].time)
            yCords.add(data[i].open)
        }
        return GraphCoordinatesModel(
            xCords,
            yCords,
            maxValue,
            minValue
        )
    }

    companion object {
        val TAG = "MainRepo"
        val SYMBOL_TO_COMPARE = "BTC"
    }
}