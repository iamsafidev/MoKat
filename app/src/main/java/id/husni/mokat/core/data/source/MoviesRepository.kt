package id.husni.mokat.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import id.husni.mokat.core.data.source.local.LocalDataSource
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.remote.RemoteDataSource
import id.husni.mokat.core.data.source.remote.network.ApiResponse
import id.husni.mokat.core.data.source.remote.response.MoviesItem
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.repository.IMoviesRepository
import id.husni.mokat.core.utils.AppExecutors
import id.husni.mokat.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors): IMoviesRepository{
    companion object{
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource, appExecutors: AppExecutors): MoviesRepository =
            instance ?: synchronized(this){
                instance ?: MoviesRepository(remoteDataSource,localDataSource,appExecutors)
            }
    }

    override fun getAllMovies(): Flow<Resources<List<Movies>>> =
        object : NetworkBoundResource<List<Movies>, List<MoviesItem>>(){
            override fun loadFromDB(): Flow<List<Movies>> {
                return localDataSource.getAllMovies().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movies>?): Boolean =
                data == null || data.isEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getAllMovies()


            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getFavouriteMovies(): Flow<List<Movies>> {
        return localDataSource.getFavorite().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavouriteMovies(movies: Movies, newState: Boolean){
        val moviesEntity = DataMapper.mapDomainToEntity(movies)
        appExecutors.diskIO().execute { localDataSource.setFavorite(moviesEntity,newState) }
    }
}