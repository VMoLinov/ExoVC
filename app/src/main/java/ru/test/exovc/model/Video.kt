package ru.test.exovc.model

data class Video(
    val id: String,
    val fileUrl: String,
    val smallThumbnailUrl: String,
    var isSelected: Boolean = false
)
