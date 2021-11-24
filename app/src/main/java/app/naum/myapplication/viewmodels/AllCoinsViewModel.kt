package app.naum.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.naum.myapplication.network.models.AllCoinsResponse
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.repositories.MainRepo
import app.naum.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AllCoinsViewModel @Inject constructor(
    private val weatherRepo: MainRepo
): ViewModel() {
    private val mutableWeatherModelState: MutableLiveData<DataState<List<CryptoModel>>> = MutableLiveData()
    val weatherState: LiveData<DataState<List<CryptoModel>>>
        get() = mutableWeatherModelState

    fun getAllCoinsList() {
        viewModelScope.launch {
            weatherRepo.getAllCoinsList().collect {
                mutableWeatherModelState.value = it
            }
        }
    }
}