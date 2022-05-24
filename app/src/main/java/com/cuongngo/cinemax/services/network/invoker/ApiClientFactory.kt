package com.cuongngo.cinemax.services.network.invoker

import com.cuongngo.cinemax.utils.Constants
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientFactory {
    companion object{
        inline fun <reified T> createService(networkConnectionInterceptor: NetworkConnectionInterceptor? = null): T{
            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(BaseInterceptor().apply {
                    addParam("language" to Constants.ENGLISH)
                    addParam("api_key" to Constants.API_KEY)
                })
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
                .protocols(listOf(Protocol.HTTP_1_1))

            if (networkConnectionInterceptor != null) {
                okkHttpClient.addInterceptor(networkConnectionInterceptor)
            }

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okkHttpClient.addInterceptor(loggingInterceptor)

            return Retrofit.Builder()
                .client(okkHttpClient.build())
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(T::class.java)
        }
    }
}