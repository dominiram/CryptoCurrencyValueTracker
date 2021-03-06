package app.naum.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.repositories.MainRepo
import app.naum.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCoinsViewModel @Inject constructor(
    private val repo: MainRepo
): ViewModel() {
    private val mutableCryptoModelListState: MutableLiveData<DataState<List<CryptoModel>>> = MutableLiveData()
    val cryptoListState: LiveData<DataState<List<CryptoModel>>>
        get() = mutableCryptoModelListState

    fun getAllCoinsList() {
        viewModelScope.launch {
            repo.getAllCoinsList().collect {
                mutableCryptoModelListState.value = it
            }
        }
    }
}