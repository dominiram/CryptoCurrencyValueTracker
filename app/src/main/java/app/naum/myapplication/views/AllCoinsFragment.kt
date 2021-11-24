package app.naum.myapplication.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.naum.myapplication.R
import app.naum.myapplication.adapters.AllCoinsAdapter
import app.naum.myapplication.adapters.AllCoinsItemSelected
import app.naum.myapplication.databinding.FragmentAllCoinsBinding
import app.naum.myapplication.network.models.AllCoinsResponse
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.AllCoinsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import kotlin.math.log

@AndroidEntryPoint
class AllCoinsFragment : Fragment(), AllCoinsItemSelected {

    private var _binding: FragmentAllCoinsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AllCoinsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        viewModel.weatherState.observe(viewLifecycleOwner, {
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
        Log.d(TAG, "showSuccessScreen: model = $list")
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
        Log.d(TAG, "onAllCoinsItemSelected: selectedModel = $model")
    }

    companion object {
        val TAG = "AllCoinsFragment"
    }
}