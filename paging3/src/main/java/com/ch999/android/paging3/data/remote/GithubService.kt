package com.ch999.android.paging3.data.remote

import com.ch999.android.paging3.BuildConfig
import com.ch999.android.paging3.model.GithubSearchPaging
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepositories(@Query("page") page: Int, @Query("per_page") perPage: Int): GithubSearchPaging

    companion object {
        fun create(): GithubService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }
}