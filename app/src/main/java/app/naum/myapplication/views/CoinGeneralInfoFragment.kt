package app.naum.myapplication.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import app.naum.myapplication.databinding.FragmentCoinGeneralInfoBinding
import app.naum.myapplication.network.models.CoinComparisonResponseWrapper
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.CoinViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.math.BigDecimal

@AndroidEntryPoint
class CoinGeneralInfoFragment(
    private val model: CryptoModel
) : Fragment() {

    private var _binding: FragmentCoinGeneralInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinGeneralInfoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        viewModel.getCoinComparisonData(model.symbol)
        setupUI()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        subscribeToObservables()

        binding.apply {
            Glide.with(requireContext())
                .load(imageBaseUrl + model.imageUrl)
                .into(this.coinGeneralInfoImageView)
            this.coinGeneralInfoCoinName.text = model.name
            this.coinGeneralInfoDescription.text = model.description
            this.coinGeneralInfoCoinWebsite.text = model.coinWebsite
            this.coinGeneralInfoTotalMined.text = model.totalMined.toString()
            this.coinGeneralInfoBlockReward.text = BigDecimal(model.blockReward).toString()
            this.coinGeneralInfoBlockTime.text = model.blockTime.toString()
        }
    }

    private fun subscribeToObservables() {
        viewModel.comparisonDataState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Loading -> (activity as MainActivity).showLoadingIndicator()
                is DataState.Success -> showSuccessState(it.data)
                is DataState.Error -> showErrorState(it.exception)
            }
        })
    }

    private fun showSuccessState(model: CoinComparisonResponseWrapper) {
        Log.d(TAG, "showSuccessState: mode = $model")
        binding.apply {
            this.coinComparedToBtc.text = model.data.BTC.toString()
            this.coinComparedToEth.text = model.data.ETH.toString()
            this.coinComparedToEvn.text = model.data.EVN.toString()
            this.coinComparedToDoge.text = model.data.DOGE.toString()
            this.coinComparedToZec.text = model.data.ZEC.toString()
            this.coinComparedToUsd.text = model.data.USD.toString()
            this.coinComparedToEur.text = model.data.EUR.toString()
        }
        (activity as MainActivity).hideLoadingIndicator()
    }

    private fun showErrorState(e: Exception) {
        e.printStackTrace()
        (activity as MainActivity).hideLoadingIndicator()
    }

    companion object {
        val TAG = "CoinGeneralInfoFragment"
        val imageBaseUrl = "https://www.cryptocompare.com"
    }

}