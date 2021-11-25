package app.naum.myapplication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.naum.myapplication.R
import app.naum.myapplication.network.models.CryptoModel

class CoinGraphViewFragment(
    private val model: CryptoModel
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_graph_view, container, false)
    }
}