package com.ch999.android.training.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch999.android.training.model.NovelOverviewBean

class NovelViewModel : ViewModel() {
    private val mNovelOverviews: MutableLiveData<MutableList<NovelOverviewBean>> = MutableLiveData()

    init {
        var source = mNovelOverviews.value
        if (source.isNullOrEmpty()) {
            source = ArrayList()
            repeat((0..100).count()) {
                source.add(NovelOverviewBean())
            }
            mNovelOverviews.value = source
        }
    }

    fun novelOverviews(): LiveData<MutableList<NovelOverviewBean>> {
        return mNovelOverviews
    }
}