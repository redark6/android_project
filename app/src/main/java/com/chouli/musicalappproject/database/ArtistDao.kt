package com.chouli.musicalappproject.database

import androidx.room.*
import com.chouli.musicalappproject.jsonResponse.Artist

@Dao
interface  ArtistDao {

    @Query("SELECT * FROM artists")
    fun getAllFavoritesArtist(): List<Artist>

    @Query("Select * from artists WHERE idArtist =:artistId")
    fun gelArtistByID(artistId: String): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtist(artist: Artist)

    @Delete
    fun deleteArtist(artist: Artist)
}