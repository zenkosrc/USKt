package com.zenkosrc.usktapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenkosrc.usktapp.api.responses.SearchResponse
import com.zenkosrc.usktapp.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    private val TAG = MainViewModel::class.simpleName

    private var searchJob: Job? = null

    var picturesResponse: MutableLiveData<Response<SearchResponse>> = MutableLiveData()

    fun getSearchPictures(query: String){

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(750)
            Log.d(TAG, "getSearchPictures ${query}" )
            val response = repository.getSearchPictures(query)
            picturesResponse.value = response
            Log.d(TAG, "getSearchPictures DONE ${query}" )
        }
    }
}