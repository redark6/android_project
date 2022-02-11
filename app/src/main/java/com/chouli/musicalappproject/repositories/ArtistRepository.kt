package com.chouli.musicalappproject.repositories

import android.content.Context
import androidx.room.Query
import com.chouli.musicalappproject.database.*
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.jsonResponse.Artist
import com.chouli.musicalappproject.services.ArtistService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class ArtistRepository(context: Context) {
    private val ARTIST_SERVICE_BASE_URL = "https://theaudiodb.com/api/v1/json/API_KEY/"
    var db: ArtistDao = DataBase.getInstance(context)?.artistDao()!!
    private var artistService: ArtistService? = null

    init {
        artistService = Retrofit.Builder()
            .baseUrl(ARTIST_SERVICE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient().newBuilder().addInterceptor(ApiCallInterceptor()).build())
            .build()
            .create(ArtistService::class.java)
    }

    suspend fun getArtistDetails(id: String) = artistService?.getArtistDetails(id)

    suspend fun searchArtist(name: String) = artistService?.searchArtist(name)

    fun getAllFavoritesArtist(): List<Artist> {
        return db.getAllFavoritesArtist()
    }

    fun getArtistByID(artistId: String): List<Artist> {
        return db.gelArtistByID(artistId)
    }

    fun addFavoriteArtist(artist: Artist) = db.addArtist(artist)

    fun deleteFavoriteArtist(artist: Artist) = db.deleteArtist(artist)

}