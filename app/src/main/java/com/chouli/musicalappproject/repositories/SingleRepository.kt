package com.chouli.musicalappproject.repositories

import com.chouli.musicalappproject.services.SingleService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SingleRepository {
    private val SINGLE_SERVICE_BASE_URL = "https://theaudiodb.com/api/v1/json/API_KEY/"

    private var singleService: SingleService? = null

    init {
        singleService = Retrofit.Builder()
            .baseUrl(SINGLE_SERVICE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient().newBuilder().addInterceptor(ApiCallInterceptor()).build())
            .build()
            .create(SingleService::class.java)
    }

    suspend fun getTopItuneSingles() = singleService?.getTopItuneSingle()

    suspend fun getAlbumTracks(id: String) = singleService?.getAlbumTracks(id)

    suspend fun getTop10LovedArtistTracks(id: String) = singleService?.getTop10LovedArtistTracks(id)

}