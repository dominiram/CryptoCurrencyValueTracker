package app.naum.myapplication.adapters

import app.naum.myapplication.network.models.CryptoModel

interface AllCoinsItemSelected {
    fun onAllCoinsItemSelected(model: CryptoModel)
}