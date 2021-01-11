package com.zenkosrc.usktapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.zenkosrc.usktapp.PictureActivity.Companion.ORIGINAL_SIZE_URL
import com.zenkosrc.usktapp.PictureActivity.Companion.REGULAR_SIZE_URL
import com.zenkosrc.usktapp.adapter.PictureListAdapter
import com.zenkosrc.usktapp.api.responses.ImageData
import com.zenkosrc.usktapp.repository.Repository
import com.zenkosrc.usktapp.utils.AppTextChangeListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var viewModel: MainViewModel
    private val pictureListAdapter by lazy { PictureListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initRecyclerview()
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

    private fun initViewModel() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.picturesResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                Log.d(TAG, response.body().toString())
                response.body()?.imageDataList?.let { pictureListAdapter.setData(it) }
            }else{
                Log.d(TAG, response.toString())
            }
        })
    }

    private fun initRecyclerview() {
        pictureListRecyclerView.adapter = pictureListAdapter
        pictureListRecyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)

        pictureListAdapter.onItemClick = { image ->
            startPictureActivity(image)
        }
    }

    private fun startPictureActivity(imageData: ImageData){
        val intent = Intent(this, PictureActivity::class.java)
        intent.putExtra(ORIGINAL_SIZE_URL, imageData.getFull())
        intent.putExtra(REGULAR_SIZE_URL, imageData.getRegular())
        startActivity(intent)
    }
}
