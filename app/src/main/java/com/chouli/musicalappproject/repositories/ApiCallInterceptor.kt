package com.chouli.musicalappproject.repositories

import okhttp3.Interceptor
import okhttp3.Response

class ApiCallInterceptor: Interceptor {

    val API_KEY = "523532"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var url = request.url().toString()
        var newUrl = url.replace("API_KEY",API_KEY)

        return chain.proceed(request.newBuilder().url(newUrl).build());
    }

}