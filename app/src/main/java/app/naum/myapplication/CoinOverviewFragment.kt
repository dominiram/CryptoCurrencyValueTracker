package app.naum.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.naum.myapplication.adapters.CoinOverviewViewPagerAdapter
import app.naum.myapplication.databinding.FragmentAllCoinsBinding
import app.naum.myapplication.databinding.FragmentCoinOverviewBinding
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.CoinOverviewViewModel
import app.naum.myapplication.views.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class CoinOverviewFragment : Fragment() {

    private val args: CoinOverviewFragmentArgs by navArgs()
    private var _binding: FragmentCoinOverviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoinOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        Log.d(TAG, "onCreateView: mode = ${args.cryptoModel}")
        _binding = FragmentCoinOverviewBinding.inflate(inflater, container, false)

        setupUI(args.coin)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUI(model: CryptoModel) {
        setupTabLayout()
        showCoinOverview(model)
        subscribeToObservables()
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager = binding.coinOverviewViewPager
        viewPager.adapter = CoinOverviewViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "General info"
                1 -> tab.text = "Graph view"
            }
        }.attach()
    }

    private fun subscribeToObservables() {
//        viewModel.coinState.observe(viewLifecycleOwner, {
//            when(it) {
//                is DataState.Loading -> (activity as MainActivity).showLoadingIndicator()
//                is DataState.Error -> showErrorState(it.exception)
//                is DataState.Success -> showCoinOverview(it.data)
//            }
//        })
    }

    private fun showErrorState(e: Exception) {
        e.printStackTrace()
        (activity as MainActivity).hideLoadingIndicator()
        Toast.makeText(
            requireContext(),
            e.message,
            Toast.LENGTH_SHORT
        ).show()
        (activity as MainActivity).onBackPressed()
    }

    private fun showCoinOverview(model: CryptoModel) {
//        (activity as MainActivity).hideLoadingIndicator()
        Log.d(TAG, "showCoinOverview: model = $model")
    }

    companion object {
        val TAG = "CoinOverviewFragment"
    }
}