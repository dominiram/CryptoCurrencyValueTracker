package app.naum.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import app.naum.myapplication.databinding.FragmentAllCoinsBinding
import dagger.hilt.android.AndroidEntryPoint

class CoinOverviewFragment : Fragment() {

    private val args: CoinOverviewFragmentArgs by navArgs()
    private var _binding: FragmentAllCoinsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: mode = ${args.cryptoModel}")
        _binding = FragmentAllCoinsBinding.inflate(inflater, container, false)

        setupUI()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI() {

    }

    companion object {
        val TAG = "CoinOverviewFragment"
    }
}