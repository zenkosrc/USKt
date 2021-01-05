package com.zenkosrc.usktapp.repository

import com.zenkosrc.usktapp.BuildConfig
import com.zenkosrc.usktapp.api.RetrofitInstance
import com.zenkosrc.usktapp.api.responses.SearchResponse
import retrofit2.Response

class Repository {

    suspend fun getSearchPictures(query: String): Response<SearchResponse>{
        return RetrofitInstance.apiService.getSearchPictures(BuildConfig.API_KEY, query)
    }
}