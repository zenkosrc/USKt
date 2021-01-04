package com.zenkosrc.usktapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zenkosrc.usktapp.api.RetrofitInstance
import com.zenkosrc.usktapp.api.responses.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitInstance.apiService.gerSearchPictures(BuildConfig.API_KEY, "London").enqueue(object :Callback<SearchResponse> {

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {

                Log.d(TAG, response.body().toString())
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {

                Log.d(TAG, t.toString())
            }
        })
    }
}
