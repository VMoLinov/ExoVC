package ru.test.exovc.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    fun getRetrofit(): SourceDao {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(SourceDao::class.java)
    }

    private const val BASE_URL = "https://dev.bgrem.deelvin.com/api/"
}
