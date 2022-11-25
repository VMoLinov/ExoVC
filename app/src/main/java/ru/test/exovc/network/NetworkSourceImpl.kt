package ru.test.exovc.network

class NetworkSourceImpl(private val api: SourceDao = RetrofitBuilder.getRetrofit()) {

    suspend fun getData() = api.getData()
}
