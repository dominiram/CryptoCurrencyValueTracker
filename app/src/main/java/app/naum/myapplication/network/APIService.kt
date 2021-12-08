package app.naum.myapplication.network

import app.naum.myapplication.network.models.AllCoinsResponse
import app.naum.myapplication.network.models.CryptoHistoricalWrapper
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/all/coinlist") //?summary=true - unfortunately this doesn't return sortOrder
    suspend fun getAllCoinsList(): AllCoinsResponse

    @GET("data/pricemulti") //&tsyms=BTC,ETH,EVN,DOGE,ZEC,USD,EUR
    suspend fun getCoinComparisonData(@Query("fsyms")coinSymbol: String,
                                      @Query("tsyms")toSymbol: String): JsonObject

    @GET("data/v2/histoday")//?fsym=BTC&tsym=USD&limit=100
    suspend fun getHistoricalDataForDay(@Query("fsym")fromSymbol: String,
                                        @Query("tsym")toSymbol: String,
                                        @Query("limit")numberOfDays: Int): CryptoHistoricalWrapper

    @GET("data/v2/histohour")//?fsym=BTC&tsym=USD&limit=100
    suspend fun getHistoricalDataForHour(@Query("fsym")fromSymbol: String,
                                         @Query("tsym")toSymbol: String,
                                         @Query("limit")numberOfDays: Int): CryptoHistoricalWrapper

    @GET("data/v2/histominute")//?fsym=BTC&tsym=USD&limit=100
    suspend fun getHistoricalDataForMinute(@Query("fsym")fromSymbol: String,
                                           @Query("tsym")toSymbol: String,
                                           @Query("limit")numberOfDays: Int): CryptoHistoricalWrapper
}
