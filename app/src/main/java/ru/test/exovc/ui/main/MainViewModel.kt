package ru.test.exovc.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.flow
import ru.test.exovc.network.NetworkSourceImpl

class MainViewModel(
    private val networkSourceImpl: NetworkSourceImpl = NetworkSourceImpl()
) : ViewModel() {

    val data = flow { emit(networkSourceImpl.getData()) }
}
