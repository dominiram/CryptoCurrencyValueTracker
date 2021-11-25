package app.naum.myapplication.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.naum.myapplication.R
import app.naum.myapplication.adapters.AllCoinsAdapter
import app.naum.myapplication.databinding.FragmentCoinGeneralInfoBinding
import app.naum.myapplication.databinding.FragmentCoinOverviewBinding
import app.naum.myapplication.network.models.CryptoModel
import com.bumptech.glide.Glide
import java.math.BigDecimal

class CoinGeneralInfoFragment(
    private val model: CryptoModel
) : Fragment() {

    private var _binding: FragmentCoinGeneralInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinGeneralInfoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setupUI()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
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

    companion object {
        val imageBaseUrl = "https://www.cryptocompare.com"
    }

}