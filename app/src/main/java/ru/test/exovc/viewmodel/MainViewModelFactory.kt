package ru.test.exovc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.test.exovc.interactor.MainInteractor
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val interactor: MainInteractor) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MainInteractor::class.java).newInstance(interactor)
    }
}
