package app.naum.myapplication.network

import app.naum.myapplication.network.models.AllCoinsResponse
import app.naum.myapplication.network.models.CryptoComparisonResponse
import app.naum.myapplication.network.models.CryptoModel
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("data/all/coinlist") //?summary=true - unfortunately this doesn't return sortOrder
    suspend fun getAllCoinsList(): AllCoinsResponse

    @GET("data/all/coinlist?fsym={coinSymbol}")
    suspend fun getCoinOverviewBySymbol(@Path("coinSymbol")symbol: String): CryptoModel

    @GET("data/pricemulti?fsyms={targetCoin}&tsyms=BTC,ETH,EVN,DOGE,ZEC,USD,EUR")
    suspend fun getValueComparison(@Path("targetCoin")coinSymbol: String): CryptoComparisonResponse //or CryptoComparisonModel

}
