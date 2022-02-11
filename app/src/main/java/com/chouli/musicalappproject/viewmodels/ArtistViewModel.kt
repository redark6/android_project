package com.chouli.musicalappproject.viewmodels

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewItem
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.jsonResponse.Artist
import com.chouli.musicalappproject.objectsModels.ArtistModel
import com.chouli.musicalappproject.repositories.AlbumRepository
import com.chouli.musicalappproject.repositories.ArtistRepository
import com.chouli.musicalappproject.repositories.SingleRepository
import kotlinx.coroutines.*

class ArtistViewModel (application: Application) : AndroidViewModel(application) {
    private var artistRepository: ArtistRepository? = null
    private var albumRepository: AlbumRepository? = null
    private var singleRepository: SingleRepository? = null
    private var context: Context? = null
    private val artistDetailResponseLiveData = MutableLiveData<Resource<ArtistModel?>>()

    fun init(context: Context) {
        this.context = context
        albumRepository = AlbumRepository(this.context!!)
        artistRepository = ArtistRepository(this.context!!)
        singleRepository = SingleRepository()
    }

    fun getArtistInfo(id: String, artistName: String) = viewModelScope.launch {
        artistDetailResponseLiveData.postValue(Resource.loading())

        val artisDetail = async { artistRepository?.getArtistDetails(id) }.await()
        val artistAlbums = async {  albumRepository?.getAlbumForArtist(id) }.await()
        val artistTracks = async {  singleRepository?.getTop10LovedArtistTracks(artistName) }.await()
        var artistLocal = async { artistRepository?.getArtistByID(id) }.await()

        if (artisDetail != null && artistAlbums != null && artistTracks != null &&
            artisDetail.isSuccessful && artistAlbums.isSuccessful && artistTracks.isSuccessful) {

            var isFavorite = false
            if (artistLocal != null) {
                if(artistLocal.isNotEmpty()){
                    isFavorite = true;
                }
            }

            var recycleViewItems = mutableListOf<MultipleViewItem>()
            artistAlbums.body()?.let {
                it.album?.let {
                    recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.albums,it.size)))
                    recycleViewItems.addAll(it)
                }
            }
            artistTracks.body()?.let { body ->
                if(body.track != null){
                    recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.prefered_tracks)))
                    recycleViewItems.addAll(body.track)
                }
            }
            artistDetailResponseLiveData.postValue(Resource.success(artisDetail.body()?.let {
                ArtistModel(
                    it,recycleViewItems,isFavorite)
            }))
        }else{
            artistDetailResponseLiveData.postValue(Resource.error(getApplication<Application>().resources.getString(R.string.error_get_infos)))
        }

    }

    fun getArtistByID(artistId: String) =  viewModelScope.launch {
        var artist = async { artistRepository?.getArtistByID(artistId) }.await()
    }

    fun addFavoriteArtist(artist: Artist) =  viewModelScope.launch {
        var artist = async { artistRepository?.addFavoriteArtist(artist) }.await()
    }

    fun deleteFavoriteArtist(artist: Artist) =  viewModelScope.launch {
        var artist = async { artistRepository?.deleteFavoriteArtist(artist) }.await()
    }

    fun getArtistDetailResponseLiveData(): LiveData<Resource<ArtistModel?>> {
        return artistDetailResponseLiveData
    }
}