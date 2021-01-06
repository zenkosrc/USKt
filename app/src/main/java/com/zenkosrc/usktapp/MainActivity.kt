package com.zenkosrc.usktapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zenkosrc.usktapp.repository.Repository
import com.zenkosrc.usktapp.utils.AppTextChangeListener

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    private fun initViewModel() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.picturesResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                Log.d(TAG, response.body().toString())
            }else{
                Log.d(TAG, response.toString())
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
            viewModel.getSearchPictures(it!!)

        })
        return super.onCreateOptionsMenu(menu)
    }
}
