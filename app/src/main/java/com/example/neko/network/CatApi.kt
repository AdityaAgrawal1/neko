package com.example.neko.network

import com.example.neko.data.model.CatListResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int
    ):List<CatListResponseItem>

    @GET("images/{id}")
    suspend fun getCat(
        @Path("id") id: String
    ):CatListResponseItem

}