package com.chouli.musicalappproject.services

import com.chouli.musicalappproject.jsonResponse.ArtistInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistService {

    @GET("artist.php")
    suspend fun getArtistDetails(@Query("i") id: String): Response<ArtistInfoResponse>

    @GET("search.php")
    suspend fun searchArtist(@Query("s") name: String): Response<ArtistInfoResponse>

}