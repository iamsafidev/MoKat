package id.husni.mokat.core.di

import android.content.Context
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.remote.RemoteDataSource
import id.husni.mokat.core.data.source.remote.network.ApiConfig

object Injection {
    fun provideInjection(context: Context): MoviesRepository{
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApi())
        return MoviesRepository.getInstance(remoteDataSource)
    }
}