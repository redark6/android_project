package com.chouli.musicalappproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.chouli.musicalappproject.jsonResponse.Album
import retrofit2.Response

@Dao
interface  AlbumDao {

    @Query("SELECT * FROM albums")
    fun getAllFavoritesAlbums(): List<Album>

    @Query("Select * from albums WHERE idAlbum =:albumId")
    fun gelAlbumByID(albumId: String): List<Album>

    @Insert(onConflict = REPLACE)
    fun addAlbum(album: Album)

    @Delete
    fun deleteAlbum(album: Album)
}