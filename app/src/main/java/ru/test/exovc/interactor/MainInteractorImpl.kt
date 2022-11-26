package ru.test.exovc.interactor

import ru.test.exovc.interactor.MainInteractorMapper.toVideoList
import ru.test.exovc.model.Video
import ru.test.exovc.network.api.NetworkSource
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
    private val networkSource: NetworkSource
) : MainInteractor {

    override suspend fun getData(): List<Video> = networkSource.getData().toVideoList()
}
