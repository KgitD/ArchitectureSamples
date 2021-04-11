package com.ch999.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch999.android.model.RouterTableItem
import com.ch999.android.model.SettingItem
import com.ch999.android.model.UserInfo

class MainViewModel : ViewModel() {
    private val mRouterTableItems: MutableLiveData<MutableList<RouterTableItem>> = MutableLiveData()
    private val mCurrentAccount: MutableLiveData<UserInfo> = MutableLiveData()
    private val mHomeDrawerItems: MutableLiveData<MutableList<SettingItem>> = MutableLiveData()

    fun routerTableItems(): LiveData<MutableList<RouterTableItem>> {
        var items = mRouterTableItems.value
        if (items.isNullOrEmpty()) {
            items = ArrayList()
            items.add(RouterTableItem("https://api.github.com/paging3/github_search", "Paging3"))
            items.add(RouterTableItem("/training/pages", "ViewPager"))
            mRouterTableItems.value = items
        }
        return mRouterTableItems
    }

    fun currentAccount(): LiveData<UserInfo> {
        mCurrentAccount.value = UserInfo()
        return mCurrentAccount
    }

    fun homeDrawerItems(): LiveData<MutableList<SettingItem>> {
        val items = ArrayList<SettingItem>()
        repeat((0..100).count()) {
            items.add(SettingItem())
        }
        mHomeDrawerItems.value = items
        return mHomeDrawerItems
    }
}