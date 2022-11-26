package ru.test.exovc.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.test.exovc.viewmodel.MainViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun viewModelFactory(): MainViewModelFactory
}
