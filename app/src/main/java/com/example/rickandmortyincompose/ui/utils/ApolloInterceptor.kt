package com.example.rickandmortyincompose.ui.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object ApolloInterceptor {

    fun getInterceptor() = Interceptor { chain ->
        val request: Request = chain.request()
        val startTime = System.nanoTime()

        Log.i(Const.TAG_APOLLO_CLIENT, "Sending request: ${request.url}")

        val response: Response = chain.proceed(request)
        Log.i(Const.TAG_APOLLO_CLIENT, "Response -> $response")
        val endTime = System.nanoTime()

        val duration = endTime - startTime
        val responseBody = response.peekBody(Long.MAX_VALUE).string()

        Log.i(Const.TAG_APOLLO_CLIENT, "Received response for ${request.url} in ${duration / 1e6}ms")
        Log.i(Const.TAG_APOLLO_CLIENT, "Response body: $responseBody")

        response
    }
}