package ru.test.exovc.network.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.test.exovc.network.model.VideoDto

interface NetworkSourceDao {

    @GET("api/backgrounds/")
    suspend fun getData(@QueryMap params: Map<String, String>): List<VideoDto>
}
