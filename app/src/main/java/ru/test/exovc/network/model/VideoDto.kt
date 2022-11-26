package ru.test.exovc.network.model

import com.google.gson.annotations.SerializedName

data class VideoDto(
    val id: String,
    @SerializedName("file_url") val fileUrl: String,
    @SerializedName("small_thumbnail_url") val smallThumbnailUrl: String,
)
