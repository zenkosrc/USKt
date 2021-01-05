package com.zenkosrc.usktapp.utils

import androidx.appcompat.widget.SearchView

class AppTextChangeListener(val onSucces: (String?) -> Unit):SearchView.OnQueryTextListener {

    override fun onQueryTextChange(newText: String?): Boolean {
        onSucces(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
}