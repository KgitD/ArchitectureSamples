package com.ch999.android.paging3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ch999.android.paging3.data.remote.GithubService
import com.ch999.android.paging3.data.source.GithubPagingSource
import com.ch999.android.paging3.model.RepositoryItem
import kotlinx.coroutines.flow.Flow

object GithubRepository {
    private const val PAGE_SIZE = 10

    private val gitHubService = GithubService.create()

    fun searchRepositories(): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { GithubPagingSource(gitHubService) }
        ).flow
    }
}