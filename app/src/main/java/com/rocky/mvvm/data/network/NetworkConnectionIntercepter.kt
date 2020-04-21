package com.rocky.mvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.rocky.mvvm.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionIntercepter(
    private val context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable())
            throw NoInternetException("Make sure you have an active  data connection")
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean {

        val conncectivityManger =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        conncectivityManger.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}