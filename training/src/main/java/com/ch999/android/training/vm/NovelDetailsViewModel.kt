package com.ch999.android.training.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch999.android.training.model.NovelDetailsBean
import com.ch999.android.training.model.NovelOverviewBean

class NovelDetailsViewModel : ViewModel() {

    private val mNovelDetailsList: MutableLiveData<MutableList<NovelDetailsBean>> = MutableLiveData()

    fun novelDetailsList(): LiveData<MutableList<NovelDetailsBean>> {
        return mNovelDetailsList
    }

    fun refreshNovelDetailsList(overviews: MutableList<NovelOverviewBean>) {
        val result: MutableList<NovelDetailsBean> = with(ArrayList<NovelDetailsBean>()) {
            overviews.map { this.add(NovelDetailsBean(it.title, it.subtitle, it.content)) }
            this
        }
        mNovelDetailsList.value = result
    }

}