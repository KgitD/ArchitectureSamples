package com.ch999.android.paging3.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ch999.android.paging3.data.GithubRepository
import com.ch999.android.paging3.model.RepositoryItem
import kotlinx.coroutines.flow.Flow

class GithubSearchViewModel : ViewModel() {

    fun searchRepositories(): Flow<PagingData<RepositoryItem>> {
        return GithubRepository.searchRepositories().cachedIn(viewModelScope)
    }

}