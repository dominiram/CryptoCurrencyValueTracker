package app.naum.myapplication.views

import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.naum.myapplication.R
import app.naum.myapplication.databinding.FragmentCoinGeneralInfoBinding
import app.naum.myapplication.databinding.FragmentCoinGraphViewBinding
import app.naum.myapplication.network.models.CryptoModel
import java.lang.Exception

class CoinGraphViewFragment(
    private val model: CryptoModel
) : Fragment() {

        private var _binding: FragmentCoinGraphViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var canvas: GraphViewCanvas

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
        return binding.root
    }

    private fun setupUI() {
        canvas = binding.graphViewCanvas
        var points: MutableList<Point> = mutableListOf()
    }
}