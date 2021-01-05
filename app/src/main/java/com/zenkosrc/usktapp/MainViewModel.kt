package com.zenkosrc.usktapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenkosrc.usktapp.api.responses.SearchResponse
import com.zenkosrc.usktapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    var picturesResponse: MutableLiveData<Response<SearchResponse>> = MutableLiveData()

    fun getSearchPictures(query: String){
        viewModelScope.launch {
            val response = repository.getSearchPictures(query)
            picturesResponse.value = response
        }
    }
}