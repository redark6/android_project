package com.chouli.musicalappproject.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.adaptaters.MultipleViewItem
import com.chouli.musicalappproject.objectsModels.SearchModel
import com.chouli.musicalappproject.repositories.AlbumRepository
import com.chouli.musicalappproject.repositories.ArtistRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

class SearchViewModel (application: Application) : AndroidViewModel(application) {
    private var artistRepository: ArtistRepository? = null
    private var albumRepository: AlbumRepository? = null
    private var context: Context? = null

    private val _searchQuery = MutableStateFlow("")
    val resutOperation: Flow<String> = _searchQuery.debounce(3000).mapLatest {
        if (it.isEmpty()) {
            return@mapLatest ""
        } else {
            return@mapLatest it
        }
    }
    private val searchResponseLiveData = MutableLiveData<Resource<SearchModel?>>()

    fun init(context: Context) {
        this.context = context
        albumRepository = AlbumRepository(this.context!!)
        artistRepository = ArtistRepository(this.context!!)
    }

    fun searchArtistsAlbums(name: String) = viewModelScope.launch {
        searchResponseLiveData.postValue(Resource.loading())

        val artists = async {  artistRepository?.searchArtist(name) }.await()
        val albums = async { albumRepository?.searchAlbumForArtist(name) }.await()

        if (artists != null && albums != null && artists.isSuccessful && albums.isSuccessful) {
            var recycleViewItems = mutableListOf<MultipleViewItem>()

            if(artists.body()?.artists  != null){
                artists.body()?.artists?.let {
                    recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.artists)))
                    recycleViewItems.addAll(it)
                }
            }
            if(albums.body()?.album  != null){
                albums.body()?.album?.let {
                    recycleViewItems.add(MultipleViewItem.Title(getApplication<Application>().resources.getString(R.string.albums_no_number)))
                    recycleViewItems.addAll(it)
                }
            }


            searchResponseLiveData.postValue(Resource.success(SearchModel(recycleViewItems)))
        }else{
            searchResponseLiveData.postValue(Resource.error(getApplication<Application>().resources.getString(R.string.error_get_infos)))
        }
    }

    fun getSearchResponseLiveData(): LiveData<Resource<SearchModel?>> {
        return searchResponseLiveData
    }

    fun setSearchQuery(query: String) {
        Log.d("change","textfunction")
        _searchQuery.value = query
    }

}