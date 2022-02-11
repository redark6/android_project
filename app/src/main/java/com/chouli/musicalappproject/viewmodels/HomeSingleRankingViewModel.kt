package com.chouli.musicalappproject.viewmodels

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.jsonResponse.TrendingResponse
import com.chouli.musicalappproject.repositories.SingleRepository
import kotlinx.coroutines.*


class HomeSingleRankingViewModel(application: Application) : AndroidViewModel(application) {
    private var singleRepository: SingleRepository? = null
    private val trendingResponseLiveData = MutableLiveData<Resource<TrendingResponse?>>()

    fun init() {
        singleRepository = SingleRepository()
    }

    fun getTopItuneSingles() = viewModelScope.launch {
        trendingResponseLiveData.postValue(Resource.loading())
        val res = async { singleRepository?.getTopItuneSingles() }.await()
        if (res != null && res.isSuccessful) {
            trendingResponseLiveData.postValue(Resource.success(res.body()))
        } else {
            trendingResponseLiveData.postValue(Resource.error(getApplication<Application>().resources.getString(R.string.error_get_infos)))
        }
    }

    fun getTrendingResponseLiveData(): MutableLiveData<Resource<TrendingResponse?>> {
        return trendingResponseLiveData
    }
}