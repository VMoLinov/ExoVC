package ru.test.exovc.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.test.exovc.interactor.MainInteractor
import ru.test.exovc.interactor.MainInteractorImpl
import ru.test.exovc.network.api.NetworkSource
import ru.test.exovc.network.api.NetworkSourceDao
import ru.test.exovc.network.api.NetworkSourceImpl

@Module
class MainModule {

    @Provides
    fun interactor(networkSource: NetworkSource): MainInteractor = MainInteractorImpl(networkSource)

    @Provides
    fun networkSource(networkSourceDao: NetworkSourceDao): NetworkSource =
        NetworkSourceImpl(networkSourceDao)

    @Provides
    fun networkDao(): NetworkSourceDao {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkSourceDao::class.java)
    }

    companion object {
        private const val BASE_URL = "https://dev.bgrem.deelvin.com/"
    }
}
