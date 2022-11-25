package ru.test.exovc.network

import retrofit2.http.GET
import retrofit2.http.Query

interface SourceDao {

    @GET("backgrounds/")
    suspend fun getData(
        @Query("group") group: String = "video",
        @Query("category_id") id: Int = 1
    ): List<VideoDto>
}
