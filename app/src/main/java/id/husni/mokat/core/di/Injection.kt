package id.husni.mokat.core.di

import android.content.Context
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.local.LocalDataSource
import id.husni.mokat.core.data.source.local.room.MoviesDatabase
import id.husni.mokat.core.data.source.remote.RemoteDataSource
import id.husni.mokat.core.data.source.remote.network.ApiConfig
import id.husni.mokat.core.utils.AppExecutors

object Injection {
    fun provideInjection(context: Context): MoviesRepository{
        val database = MoviesDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApi())
        val localDataSource = LocalDataSource.getInstance(database.moviesDao())
        val appExecutors = AppExecutors()
        return MoviesRepository.getInstance(remoteDataSource,localDataSource,appExecutors)
    }
}