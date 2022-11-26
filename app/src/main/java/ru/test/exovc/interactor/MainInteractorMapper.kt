package ru.test.exovc.interactor

import ru.test.exovc.model.Video
import ru.test.exovc.network.model.VideoDto

object MainInteractorMapper {

    fun List<VideoDto>.toVideoList() = mapIndexed { index, videoDto ->
        Video(
            id = videoDto.id,
            fileUrl = videoDto.fileUrl,
            smallThumbnailUrl = videoDto.smallThumbnailUrl,
            isSelected = index == SELECTED_DEFAULT
        )
    }

    private const val SELECTED_DEFAULT = 1
}
