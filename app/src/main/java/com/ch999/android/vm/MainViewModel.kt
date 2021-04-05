package com.ch999.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch999.android.model.RouterTableItem

class MainViewModel : ViewModel() {
    private val mRouterTableItems: MutableLiveData<MutableList<RouterTableItem>> = MutableLiveData()

    fun routerTableItems(): LiveData<MutableList<RouterTableItem>> {
        var items = mRouterTableItems.value
        if (items.isNullOrEmpty()) {
            items = ArrayList()
            items.add(RouterTableItem("https://api.github.com/paging3/github_search", "Github仓库"))
            items.add(RouterTableItem("/paging3/github_search", "Github仓库"))
            mRouterTableItems.value = items
        }
        return mRouterTableItems
    }
}