package app.naum.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.naum.myapplication.R
import app.naum.myapplication.adapters.AllCoinsAdapter
import app.naum.myapplication.adapters.AllCoinsItemSelected
import app.naum.myapplication.databinding.FragmentAllCoinsBinding
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.AllCoinsViewModel
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
        binding.swipeRefreshContainer.setOnRefreshListener {
            viewModel.getAllCoinsList()
            binding.swipeRefreshContainer.isRefreshing = false
        }
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun showLoadingIndicator() {
        (activity as MainActivity).showLoadingIndicator()
    }

    private fun showErrorState(e: Exception) {
        e.printStackTrace()
        (activity as MainActivity).hideLoadingIndicator()
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.error_occured),
            Toast.LENGTH_LONG
        ).show()

    }

    private fun showSuccessScreen(list: List<CryptoModel>) {
        if(list.isNullOrEmpty()) {
            showErrorState(Exception())
        }
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

    override fun onAllCoinsItemSelected(model: CryptoModel) {
        val action = AllCoinsFragmentDirections.actionBlankFragmentToCoinOverviewFragment(model)
        findNavController().navigate(action)
    }

    companion object {
        val TAG = "AllCoinsFragment"
    }
}