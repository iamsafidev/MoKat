package id.husni.mokat.core.data.source

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.remote.RemoteDataSource
import id.husni.mokat.core.data.source.remote.response.MoviesItem

class MoviesRepository private constructor(private val remoteDataSource: RemoteDataSource){
    companion object{
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): MoviesRepository =
            instance ?: synchronized(this){
                instance ?: MoviesRepository(remoteDataSource)
            }
    }

    fun getAllMovies(): LiveData<List<MoviesItem>> = remoteDataSource.getAllMovies()
}