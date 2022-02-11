package com.chouli.musicalappproject.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewItem
import com.chouli.musicalappproject.jsonResponse.Album
import com.chouli.musicalappproject.objectsModels.AlbumModel
import com.chouli.musicalappproject.repositories.AlbumRepository
import com.chouli.musicalappproject.repositories.SingleRepository
import kotlinx.coroutines.*

class AlbumViewModel (application: Application) : AndroidViewModel(application) {
    private var albumRepository: AlbumRepository? = null
    private var singleRepository: SingleRepository? = null
    private var context: Context? = null
    private val albumInfoResponseLiveData = MutableLiveData<Resource<AlbumModel?>>()

    fun init(context: Context) {
        this.context = context
        albumRepository =  AlbumRepository(this.context!!)
        singleRepository = SingleRepository()
    }

    fun getAlbumInfo(id: String) = viewModelScope.launch {
        albumInfoResponseLiveData.postValue(Resource.loading())

        val album = async { albumRepository?.getAlbumDetail(id) }.await()
        val tracks = async {  singleRepository?.getAlbumTracks(id) }.await()
        var albumLocal = async { albumRepository?.getAlbumByID(id) }.await()

        if (album != null && tracks != null  &&
            album.isSuccessful && tracks.isSuccessful) {

            var isFavorite = false
            if (albumLocal != null) {
                if(albumLocal.isNotEmpty()){
                    isFavorite = true;
                }
            }

            var recycleViewItems = mutableListOf<MultipleViewItem>()
            tracks.body()?.let {
                recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.tracks)))
                recycleViewItems.addAll(it.track)
            }

            albumInfoResponseLiveData.postValue(Resource.success(album.body()?.let { tracks.body()?.track?.let { it1 ->
                AlbumModel(it,recycleViewItems,
                    it1.size,isFavorite)
            } }))
        }else{
            albumInfoResponseLiveData.postValue(Resource.error(getApplication<Application>().resources.getString(R.string.error_get_infos)))
        }
    }

    fun getAlbumByID(albumId: String) =  viewModelScope.launch {
        var album = async { albumRepository?.getAlbumByID(albumId) }.await()
    }

    fun addFavoriteAlbum(album: Album) =  viewModelScope.launch {
        var album = async { albumRepository?.addFavoriteAlbum(album) }.await()
    }

    fun deleteFavoriteAlbum(album: Album) =  viewModelScope.launch {
        var album = async { albumRepository?.deleteFavoriteAlbum(album) }.await()
    }

    fun getArtistAlbumDetailResponseLiveData(): LiveData<Resource<AlbumModel?>> {
        return albumInfoResponseLiveData
    }

}