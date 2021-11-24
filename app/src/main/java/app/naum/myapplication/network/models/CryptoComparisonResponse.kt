package app.naum.myapplication.network.models

data class CryptoComparisonResponse(
    val comparison: CryptoComparisonModel
)

data class CryptoComparisonModel(
    val BTC: Double,
    val ETH: Double,
    val EVN: Double,
    val DOGE: Double,
    val ZEC: Double,
    val USD: Double,
    val EUR: Double
)