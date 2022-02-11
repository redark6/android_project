package com.chouli.musicalappproject.repositories

import android.content.Context
import com.chouli.musicalappproject.database.AlbumDao
import com.chouli.musicalappproject.database.DataBase
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.services.AlbumService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AlbumRepository(context: Context) {
    private val ALBUM_SERVICE_BASE_URL = "https://theaudiodb.com/api/v1/json/API_KEY/"
    var db: AlbumDao = DataBase.getInstance(context)?.albumDao()!!
    private var albumService: AlbumService? = null

    init {
        albumService = Retrofit.Builder()
            .baseUrl(ALBUM_SERVICE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient().newBuilder().addInterceptor(ApiCallInterceptor()).build())
            .build()
            .create(AlbumService::class.java)
    }

    suspend fun getTopItuneAlbums() = albumService?.getTopItuneAlbums()

    suspend fun getAlbumDetail(id: String) = albumService?.getAlbumDetail(id)

    suspend fun getAlbumForArtist(id: String) = albumService?.getAlbumForArtist(id)

    suspend fun searchAlbumForArtist(name: String) = albumService?.searchAlbumForArtist(name)

    fun getAllFavoritesAlbums(): List<Album> {
        return db.getAllFavoritesAlbums()
    }

    fun getAlbumByID(albumId: String): List<Album> {
        return db.gelAlbumByID(albumId)
    }

    fun addFavoriteAlbum(album: Album) = db.addAlbum(album)

    fun deleteFavoriteAlbum(album: Album) = db.deleteAlbum(album)

}