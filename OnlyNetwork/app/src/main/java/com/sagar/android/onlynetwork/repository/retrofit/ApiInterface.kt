package com.sagar.android.onlynetwork.repository.retrofit

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getTrendingNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: String,
        @Query("pagesize") pageSize: String
    ): Observable<Response<ResponseBody>>

    @GET("everything")
    fun filterNews(
        @Query("q") queryKeyWord: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: String,
        @Query("pagesize") pageSize: String
    ): Observable<Response<ResponseBody>>
}