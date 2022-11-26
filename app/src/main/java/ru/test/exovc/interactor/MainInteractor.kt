package ru.test.exovc.interactor

import ru.test.exovc.model.Video

interface MainInteractor {

    suspend fun getData(): List<Video>
}
