package com.cuongngo.cinemax.services.network.invoker

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
): Interceptor {
    private val applicationContext = context.applicationContext
    private var toast: Toast? = null
    private val handler = Handler(Looper.getMainLooper())
    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isInternetAvailabel()) {
            val urlString  =  chain.request().url.toString()
            if(!urlString.contains("notifications/status")){
                //connection failed
            }
            throw NoInternetException("Make sure you have an active data connection")
        }

        return chain.proceed(chain.request())

    }

    private fun isInternetAvailabel() : Boolean {

        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}