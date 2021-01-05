package com.zenkosrc.usktapp.api.services

import com.zenkosrc.usktapp.api.responses.SearchResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface APIService {

    @Headers("Content-Type: application/json")
    @GET("search/photos")
    suspend fun getSearchPictures(@Query("client_id") key: String,
                          @Query("query") query: String
    ): Response<SearchResponse>
}