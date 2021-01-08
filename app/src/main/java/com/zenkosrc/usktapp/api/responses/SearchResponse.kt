package com.zenkosrc.usktapp.api.responses

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @SerializedName("results")
    val imageDataList: List<ImageData>)

data class ImageData(

    @SerializedName("description")
    val description:String,

    @SerializedName("urls")
    val urls: Map<String, String>
){

    fun getSmall(): String? {
        return urls["small"]
    }

    fun getRegular(): String? {
        return urls["regular"]
    }

    fun getFull(): String? {
        return urls["full"]
    }
}