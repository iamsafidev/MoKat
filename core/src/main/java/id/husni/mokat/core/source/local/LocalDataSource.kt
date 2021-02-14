package id.husni.mokat.core.source.local

import id.husni.mokat.core.source.local.entity.MoviesEntity
import id.husni.mokat.core.source.local.room.MoviesDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val moviesDao: MoviesDao){

    fun getAllMovies() : Flow<List<MoviesEntity>> = moviesDao.getAllMovies()

    fun getFavorite(): Flow<List<MoviesEntity>> = moviesDao.getFavorite()

    suspend fun insertMovies(movies: List<MoviesEntity>) = moviesDao.insertMovies(movies)

    fun setFavorite(moviesEntity: MoviesEntity, newState: Boolean){
        moviesEntity.isFavorite = newState
        moviesDao.updateMovies(moviesEntity)
    }
}