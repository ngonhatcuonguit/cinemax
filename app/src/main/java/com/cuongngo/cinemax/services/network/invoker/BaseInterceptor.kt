package com.cuongngo.cinemax.services.network.invoker

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class BaseInterceptor : Interceptor {
    private val headers: HashMap<String, String> = HashMap()

    fun addHeader(header: Pair<String, String>){
        headers[header.first] = header.second
    }

    private val params: HashMap<String, String> = HashMap()

    fun addParam(param: Pair<String, String>){
        params[param.first] = param.second
    }

    fun setToken(token: String){
        headers["Authorization"] = token
    }

    override fun intercept(chain: Chain): Response {
        var request = chain.request().newBuilder()
        var originalHttpUrl = chain.request().url
        var newUrl = originalHttpUrl.newBuilder()

        headers.forEach {
            request.addHeader(it.key, it.value)
        }
        params.forEach{
            newUrl.addQueryParameter(it.key,it.value)
        }
        request.url(newUrl.build())
        return chain.proceed(request.build())
    }
}