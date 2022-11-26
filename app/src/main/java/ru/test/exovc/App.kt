package ru.test.exovc

import android.app.Application
import ru.test.exovc.di.AppComponent
import ru.test.exovc.di.DaggerAppComponent

class App : Application() {

    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        applicationComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
    }

    companion object {
        lateinit var instance: App
        fun getComponent() = instance.applicationComponent
    }
}
