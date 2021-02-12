package id.husni.mokat.core.data.source

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.local.LocalDataSource
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.remote.RemoteDataSource
import id.husni.mokat.core.data.source.remote.network.ApiResponse
import id.husni.mokat.core.data.source.remote.response.MoviesItem
import id.husni.mokat.core.utils.AppExecutors
import id.husni.mokat.core.utils.DataMapper

class MoviesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors){
    companion object{
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource, appExecutors: AppExecutors): MoviesRepository =
            instance ?: synchronized(this){
                instance ?: MoviesRepository(remoteDataSource,localDataSource,appExecutors)
            }
    }

    fun getAllMovies(): LiveData<Resources<List<MoviesEntity>>> =
        object : NetworkBoundResource<List<MoviesEntity>, List<MoviesItem>>(appExecutors){
            override fun loadFromDB(): LiveData<List<MoviesEntity>> {
                return localDataSource.getAllMovies()
            }

            override fun shouldFetch(data: List<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()


            override fun createCall(): LiveData<ApiResponse<List<MoviesItem>>> {
                return remoteDataSource.getAllMovies()
            }

            override fun saveCallResult(data: List<MoviesItem>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()

    fun getFavouriteMovies(): LiveData<List<MoviesEntity>> = localDataSource.getFavorite()

    fun setFavouriteMovies(moviesEntity: MoviesEntity, newState: Boolean){
        appExecutors.diskIO().execute { localDataSource.setFavorite(moviesEntity,newState) }
    }
}