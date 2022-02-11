package com.chouli.musicalappproject.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.jsonResponse.TrendingResponse
import com.chouli.musicalappproject.repositories.AlbumRepository
import kotlinx.coroutines.*

class HomeAlbumRankingViewModel (application: Application) : AndroidViewModel(application) {
    private var albumRepository: AlbumRepository? = null
    private val trendingResponseLiveData = MutableLiveData<Resource<TrendingResponse?>>()
    private var context: Context? = null

    fun init(context: Context) {
        this.context = context
        albumRepository = AlbumRepository(this.context!!)
    }

    fun getTopItuneAlbums() = viewModelScope.launch {
        trendingResponseLiveData.postValue(Resource.loading())
        val res = async { albumRepository?.getTopItuneAlbums() }.await()
        if (res != null && res.isSuccessful) {
            trendingResponseLiveData.postValue(Resource.success(res.body()))
        } else {
            trendingResponseLiveData.postValue(Resource.error(getApplication<Application>().resources.getString(R.string.error_get_infos)))
        }
    }

    fun getTrendingResponseLiveData(): LiveData<Resource<TrendingResponse?>> {
        return trendingResponseLiveData
    }
}