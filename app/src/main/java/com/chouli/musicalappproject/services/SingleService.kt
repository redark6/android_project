package com.chouli.musicalappproject.services

import com.chouli.musicalappproject.jsonResponse.TrackInfoResponse
import com.chouli.musicalappproject.jsonResponse.TrendingResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SingleService {

    @GET("trending.php?country=us&type=itunes&format=singles")
    suspend fun getTopItuneSingle(): Response<TrendingResponse>

    @GET("track.php")
    suspend fun getAlbumTracks(@Query("m") id: String): Response<TrackInfoResponse>

    @GET("track-top10.php")
    suspend fun getTop10LovedArtistTracks(@Query("s") id: String): Response<TrackInfoResponse>
}