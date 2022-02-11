package com.chouli.musicalappproject.services

import com.chouli.musicalappproject.jsonResponse.TrendingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.chouli.musicalappproject.jsonResponse.AlbumInfoResponse
import retrofit2.Response


interface AlbumService {

    @GET("trending.php?country=us&type=itunes&format=albums")
    suspend fun getTopItuneAlbums(): Response<TrendingResponse>

    @GET("album.php")
    suspend fun getAlbumDetail(@Query("m") id: String): Response<AlbumInfoResponse>

    @GET("album.php")
    suspend fun getAlbumForArtist(@Query("i") id: String): Response<AlbumInfoResponse>

    @GET("searchalbum.php")
    suspend fun searchAlbumForArtist(@Query("s") name: String): Response<AlbumInfoResponse>

}