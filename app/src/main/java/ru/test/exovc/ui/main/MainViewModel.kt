package ru.test.exovc.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.test.exovc.network.NetworkSourceImpl
import ru.test.exovc.network.VideoDto

class MainViewModel(
    private val networkSourceImpl: NetworkSourceImpl = NetworkSourceImpl()
) : ViewModel() {

    val data = flow { emit(networkSourceImpl.getData()) }
    val activeVideo: MutableStateFlow<VideoDto?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            activeVideo.emit(data.first()[Companion.SELECTED_DEFAULT])
        }
    }

    fun clickItem(videoDto: VideoDto) {
        viewModelScope.launch {
            activeVideo.emit(videoDto)
        }
    }

    companion object {
        private const val SELECTED_DEFAULT = 0
    }
}
