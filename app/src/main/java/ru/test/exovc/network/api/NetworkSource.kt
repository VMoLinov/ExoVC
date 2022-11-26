package ru.test.exovc.network.api

import ru.test.exovc.network.model.VideoDto

interface NetworkSource {

    suspend fun getData(): List<VideoDto>
}
