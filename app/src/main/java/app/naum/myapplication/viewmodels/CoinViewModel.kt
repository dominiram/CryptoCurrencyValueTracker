package app.naum.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.naum.myapplication.models.GraphCoordinatesModel
import app.naum.myapplication.network.models.CoinComparisonResponseWrapper
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

    private val mutableComparisonDataState: MutableLiveData<DataState<CoinComparisonResponseWrapper>>
            = MutableLiveData()
    val comparisonDataState: LiveData<DataState<CoinComparisonResponseWrapper>>
        get() = mutableComparisonDataState

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

    fun getCoinComparisonData(symbol: String) {
        viewModelScope.launch {
            repo.getCoinComparisonData(symbol).collect {
                mutableComparisonDataState.value = it
            }
        }
    }
}