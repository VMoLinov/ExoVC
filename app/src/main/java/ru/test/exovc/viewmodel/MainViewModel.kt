package ru.test.exovc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.test.exovc.interactor.MainInteractor
import ru.test.exovc.model.Video
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) : ViewModel() {

    val data = flow { emit(interactor.getData()) }
    val activeVideo: MutableStateFlow<Video?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            activeVideo.emit(data.first().first { it.isSelected })
        }
    }

    fun clickItem(video: Video) {
        viewModelScope.launch {
            activeVideo.emit(video)
        }
    }
}
