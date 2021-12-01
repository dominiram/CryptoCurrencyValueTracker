package app.naum.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.naum.myapplication.models.GraphCoordinatesModel
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.network.models.HistoricalCryptoData
import app.naum.myapplication.repositories.MainRepo
import app.naum.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val repo: MainRepo
): ViewModel(){
    private val mutableHistoricalDataListState:
            MutableLiveData<DataState<GraphCoordinatesModel>> = MutableLiveData()
    val historicalDataListState: LiveData<DataState<GraphCoordinatesModel>>
        get() = mutableHistoricalDataListState

//    private val mutableHistoricalDataListState:
//            MutableLiveData<DataState<List<HistoricalCryptoData>>> = MutableLiveData()
//    val historicalDataListState: LiveData<DataState<List<HistoricalCryptoData>>>
//        get() = mutableHistoricalDataListState

    fun getHistoricalDataForDay(fromSymbol: String, numberOfDays: Int) {
        viewModelScope.launch {
            repo.getHistoricalDataForDay(fromSymbol, numberOfDays).collect {
                mutableHistoricalDataListState.value = it
            }
        }
    }

    fun getHistoricalDataForHour(fromSymbol: String, numberOfDays: Int) {
        viewModelScope.launch {
            repo.getHistoricalDataForHour(fromSymbol, numberOfDays).collect {
                mutableHistoricalDataListState.value = it
            }
        }
    }

    fun getHistoricalDataForMinute(fromSymbol: String, numberOfDays: Int) {
        viewModelScope.launch {
            repo.getHistoricalDataForMinute(fromSymbol, numberOfDays).collect {
                mutableHistoricalDataListState.value = it
            }
        }
    }
}