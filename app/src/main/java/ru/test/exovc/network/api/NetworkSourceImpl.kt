package ru.test.exovc.network.api

import ru.test.exovc.network.params.ApiParams

class NetworkSourceImpl(private val api: NetworkSourceDao) : NetworkSource {

    override suspend fun getData() = api.getData(ApiParams().toMap())
}
