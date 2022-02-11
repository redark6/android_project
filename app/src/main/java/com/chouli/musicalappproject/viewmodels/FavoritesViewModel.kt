package com.chouli.musicalappproject.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewItem
import com.chouli.musicalappproject.objectsModels.FavoritesModel
import com.chouli.musicalappproject.repositories.AlbumRepository
import com.chouli.musicalappproject.repositories.ArtistRepository
import kotlinx.coroutines.*

class FavoritesViewModel (application: Application) : AndroidViewModel(application) {
    private var artistRepository: ArtistRepository? = null
    private var albumRepository: AlbumRepository? = null
    private var context: Context? = null

    private val FavoritesAritistsAlbumsesponseLiveData = MutableLiveData<Resource<FavoritesModel?>>()

    fun init(context: Context) {
        this.context = context
        albumRepository = AlbumRepository(this.context!!)
        artistRepository = ArtistRepository(this.context!!)
    }

    fun getFavoritesAritistsAlbums() = viewModelScope.launch {
        FavoritesAritistsAlbumsesponseLiveData.postValue(Resource.loading())

        val artists = async {  artistRepository?.getAllFavoritesArtist() }.await()
        val albums = async { albumRepository?.getAllFavoritesAlbums() }.await()

        if (artists != null && albums != null) {
            var recycleViewItems = mutableListOf<MultipleViewItem>()

            if(artists.isNotEmpty()){
                recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.artists)))
                recycleViewItems.addAll(artists)
            }
            if(albums.isNotEmpty()){
                recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.albums_no_number)))
                recycleViewItems.addAll(albums)
            }

            FavoritesAritistsAlbumsesponseLiveData.postValue(Resource.success(FavoritesModel(recycleViewItems)))
        }else{
            FavoritesAritistsAlbumsesponseLiveData.postValue(Resource.error(getApplication<Application>().resources.getString(R.string.error_get_infos)))
        }
    }

    fun getFavoritesAritistsAlbumsResponseLiveData(): LiveData<Resource<FavoritesModel?>> {
        return FavoritesAritistsAlbumsesponseLiveData
    }

}