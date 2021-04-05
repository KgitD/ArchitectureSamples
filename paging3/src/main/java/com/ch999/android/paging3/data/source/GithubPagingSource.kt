package com.ch999.android.paging3.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ch999.android.paging3.data.remote.GithubService
import com.ch999.android.paging3.model.RepositoryItem

class GithubPagingSource(private val service: GithubService) : PagingSource<Int, RepositoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        return try {
            val key = params.key ?: 1
            val loadSize = params.loadSize
            val response = service.searchRepositories(key, loadSize)
            val data = response.items ?: ArrayList()
            val prevKey = if (key > 1) key - 1 else null
            val nextKey = if (data?.size == loadSize) key + 1 else null
            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}