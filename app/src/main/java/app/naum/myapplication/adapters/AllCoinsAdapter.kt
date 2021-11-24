package app.naum.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.naum.myapplication.databinding.ListItemAllCoinsBinding
import app.naum.myapplication.network.models.CryptoModel
import com.bumptech.glide.Glide

class AllCoinsAdapter(
    private val allCoinsList: List<CryptoModel>,
    private val coinsSelectedInterface: AllCoinsItemSelected,
    private val context: Context
): RecyclerView.Adapter<AllCoinsAdapter.ViewHolder>() {
    private lateinit var binding: ListItemAllCoinsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemAllCoinsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.listItemCryptoName.text = allCoinsList[position].name
        binding.listItemCryptoSymbol.text = allCoinsList[position].symbol
        Glide.with(context)
            .load(imageBaseUrl+allCoinsList[position].imageUrl)
            .into(binding.listItemIv)
    }

    override fun getItemCount() = allCoinsList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        init { itemView.setOnClickListener(this) }

        override fun onClick(p0: View?) =
            coinsSelectedInterface.onAllCoinsItemSelected(allCoinsList[adapterPosition].symbol)
    }

    companion object {
        val imageBaseUrl = "https://www.cryptocompare.com"
    }
}