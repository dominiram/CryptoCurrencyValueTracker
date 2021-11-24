package app.naum.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.naum.myapplication.adapters.AllCoinsAdapter
import app.naum.myapplication.adapters.AllCoinsItemSelected
import app.naum.myapplication.databinding.FragmentAllCoinsBinding
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.AllCoinsViewModel
import app.naum.myapplication.viewmodels.CoinOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AllCoinsFragment : Fragment(), AllCoinsItemSelected {

    private var _binding: FragmentAllCoinsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AllCoinsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAllCoinsBinding.inflate(inflater, container, false)

        viewModel.getAllCoinsList()
        setupUI()
        return binding.root
    }

    //to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Helper methods

    private fun subscribeToObservables() {
        viewModel.cryptoListState.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> showLoadingIndicator()
                is DataState.Error -> showErrorState(it.exception)
                is DataState.Success -> showSuccessScreen(it.data)
            }
        })
    }

    private fun setupUI() {
        subscribeToObservables()
    }

    private fun showLoadingIndicator() {
        (activity as MainActivity).showLoadingIndicator()
    }

    private fun showErrorState(e: Exception) {
        (activity as MainActivity).hideLoadingIndicator()
        e.printStackTrace()
        // TODO: 17/11/2021
    }

    private fun showSuccessScreen(list: List<CryptoModel>) {
        setupRecyclerView(list)
        (activity as MainActivity).hideLoadingIndicator()
    }

    private fun setupRecyclerView(list: List<CryptoModel>) {
        binding.allCoinsRv.layoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )
        val adapter = AllCoinsAdapter(
            list,
            this,
            requireContext()
        )
        adapter.setHasStableIds(true)
        binding.allCoinsRv.adapter = adapter
    }

    override fun onAllCoinsItemSelected(symbol: String) {
        val action = AllCoinsFragmentDirections.actionBlankFragmentToCoinOverviewFragment(symbol)
        findNavController().navigate(action)
    }

    companion object {
        val TAG = "AllCoinsFragment"
    }
}