package app.naum.myapplication.views

import android.annotation.SuppressLint
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
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CoinGraphViewFragment(
    private val model: CryptoModel
) : Fragment() {

    private var _binding: FragmentCoinGraphViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var canvas: GraphViewCanvas
    private val viewModel: CoinViewModel by viewModels()
    private var comparisonByDaySelected = true
    private var comparisonByHourSelected = false
    private var comparisonByMinuteSelected = false

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
        viewModel.getHistoricalDataForDay(model.symbol, 7)
        return binding.root
    }

    private fun setupUI() {
        canvas = binding.graphViewCanvas
        subscribeToObservables()
        setupButtonLabelsForDay()
        setupListeners()
    }

    private fun setupListeners() {
        binding.compareToDaysBtn.setOnClickListener {
            setupButtonLabelsForDay()
            viewModel.getHistoricalDataForDay(model.symbol, getMinimumLimit())
        }
        binding.compareToHoursBtn.setOnClickListener {
            setupButtonLabelsForHour()
            viewModel.getHistoricalDataForHour(model.symbol, getMinimumLimit())
        }
        binding.compareToMinutesBtn.setOnClickListener {
            setupButtonLabelsForMinute()
            viewModel.getHistoricalDataForMinute(model.symbol, getMinimumLimit())
        }
        binding.graphMinTimeCompare.setOnClickListener {
            when{
                comparisonByDaySelected -> viewModel
                    .getHistoricalDataForDay(model.symbol, getMinimumLimit())
                comparisonByHourSelected -> viewModel
                    .getHistoricalDataForHour(model.symbol, getMinimumLimit())
                comparisonByMinuteSelected -> viewModel
                    .getHistoricalDataForMinute(model.symbol, getMinimumLimit())
            }
        }
        binding.graphMidTimeCompare.setOnClickListener {
            when{
                comparisonByDaySelected -> viewModel
                    .getHistoricalDataForDay(model.symbol, getMediumLimit())
                comparisonByHourSelected -> viewModel
                    .getHistoricalDataForHour(model.symbol, getMediumLimit())
                comparisonByMinuteSelected -> viewModel
                    .getHistoricalDataForMinute(model.symbol, getMediumLimit())
            }
        }
        binding.graphMaxTimeCompare.setOnClickListener {
            when{
                comparisonByDaySelected -> viewModel
                    .getHistoricalDataForDay(model.symbol, getMaximumLimit())
                comparisonByHourSelected -> viewModel
                    .getHistoricalDataForHour(model.symbol, getMaximumLimit())
                comparisonByMinuteSelected -> viewModel
                    .getHistoricalDataForMinute(model.symbol, getMaximumLimit())
            }
        }
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
        Log.d(TAG, "convertTimestampToDate: ts = $ts")
        val sdf = if(comparisonByDaySelected)
            SimpleDateFormat("MM/dd/yyyy")
        else SimpleDateFormat("HH:mm")
        Log.d(TAG, "convertTimestampToDate: ts.toLong = ${ts.toLong()}")
        val date = Date(ts.toLong()*1000)
        Log.d(TAG, "convertTimestampToDate: date = $date")
        return sdf.format(date)
    }

    @SuppressLint("SetTextI18n")
    private fun setupButtonLabelsForDay() {
        binding.graphMinTimeCompare.text = "1W"
        binding.graphMidTimeCompare.text = "2W"
        binding.graphMaxTimeCompare.text = "1M"

        comparisonByDaySelected = true
        comparisonByHourSelected = false
        comparisonByMinuteSelected = false
    }

    @SuppressLint("SetTextI18n")
    private fun setupButtonLabelsForHour() {
        binding.graphMinTimeCompare.text = "1D"
        binding.graphMidTimeCompare.text = "3D"
        binding.graphMaxTimeCompare.text = "1W"

        comparisonByDaySelected = false
        comparisonByHourSelected = true
        comparisonByMinuteSelected = false
    }

    @SuppressLint("SetTextI18n")
    private fun setupButtonLabelsForMinute() {
        binding.graphMinTimeCompare.text = "1H"
        binding.graphMidTimeCompare.text = "3H"
        binding.graphMaxTimeCompare.text = "1D"

        comparisonByDaySelected = false
        comparisonByHourSelected = false
        comparisonByMinuteSelected = true
    }

    private fun getMinimumLimit(): Int {
        when {
            comparisonByDaySelected -> return 7
            comparisonByHourSelected -> return 24
            comparisonByMinuteSelected -> return 60
        }
        return 1
    }

    private fun getMediumLimit(): Int {
        when {
            comparisonByDaySelected -> return 14
            comparisonByHourSelected -> return 72
            comparisonByMinuteSelected -> return 180
        }
        return 1
    }

    private fun getMaximumLimit(): Int {
        when {
            comparisonByDaySelected -> return 30
            comparisonByHourSelected -> return 168
            comparisonByMinuteSelected -> return 1440
        }
        return 1
    }

    companion object {
        val TAG = "CoinGraphViewFragment"
    }
}