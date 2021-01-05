package com.zenkosrc.usktapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.zenkosrc.usktapp.api.RetrofitInstance
import com.zenkosrc.usktapp.api.responses.SearchResponse
import com.zenkosrc.usktapp.utils.AppTextChangeListener
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setQueryHint(resources.getString(R.string.action_search))

        searchView.setOnQueryTextListener(AppTextChangeListener{
            Log.d(TAG, it)

        })
        return super.onCreateOptionsMenu(menu)
    }
}
