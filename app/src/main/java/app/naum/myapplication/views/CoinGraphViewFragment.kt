package app.naum.myapplication.views

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import app.naum.myapplication.databinding.FragmentCoinGraphViewBinding
import app.naum.myapplication.models.GraphCoordinatesModel
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.network.models.HistoricalCryptoData
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class CoinGraphViewFragment(
    private val model: CryptoModel
) : Fragment() {

    private var _binding: FragmentCoinGraphViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var canvas: GraphViewCanvas
    private val viewModel: CoinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinGraphViewBinding.inflate(
            layoutInflater,
            container,
            false
        )

        setupUI()
        viewModel.getHistoricalDataForDayBySymbol(model.symbol, 7)
        return binding.root
    }

    private fun setupUI() {
        canvas = binding.graphViewCanvas
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        viewModel.historicalDataListState.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> (activity as MainActivity).showLoadingIndicator()
                is DataState.Error -> showErrorState(it.exception)
                is DataState.Success -> showCoinGraphView(it.data)
            }
        })
    }

    private fun showErrorState(e: Exception) {
        // TODO: 27/11/2021
        e.printStackTrace()
        (activity as MainActivity).hideLoadingIndicator()
    }

    private fun showCoinGraphView(coordinatesModel: GraphCoordinatesModel) {
        Log.d(TAG, "showCoinOverview: list = $coordinatesModel")
        canvas.setGraphCoordinatesModel(coordinatesModel)
        fillCoordinatesValues(coordinatesModel)
        (activity as MainActivity).hideLoadingIndicator()
    }

    private fun fillCoordinatesValues(coordinatesModel: GraphCoordinatesModel) {
        binding.graphHighestValue.text = coordinatesModel.yMax.toString()
        binding.graphLowesttValue.text = coordinatesModel.yMin.toString()
        binding.graphMidValue.text = coordinatesModel.yCoordinates[coordinatesModel.yCoordinates.size/2].toString()
        binding.graphFirstTime.text = convertTimestampToDate(coordinatesModel.xCoordinates[0].toString())
        binding.graphMidTime.text = convertTimestampToDate(coordinatesModel.xCoordinates[
                coordinatesModel.xCoordinates.size/2
        ].toString())
        binding.graphLastTime.text = convertTimestampToDate(coordinatesModel.xCoordinates[
                coordinatesModel.xCoordinates.size-1
        ].toString())
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertTimestampToDate(ts: String): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val date = Date(ts.toLong())
        return sdf.format(date)
    }

    companion object {
        val TAG = "CoinGraphViewFragment"
    }
}