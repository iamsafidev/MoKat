package id.husni.mokat.core.data.source.local

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.local.room.MoviesDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val moviesDao: MoviesDao){
    companion object{
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao): LocalDataSource =
            instance ?: synchronized(this){
                instance ?: LocalDataSource(moviesDao)
            }
    }

    fun getAllMovies() : Flow<List<MoviesEntity>> = moviesDao.getAllMovies()

    fun getFavorite(): Flow<List<MoviesEntity>> = moviesDao.getFavorite()

    suspend fun insertMovies(movies: List<MoviesEntity>) = moviesDao.insertMovies(movies)

    fun setFavorite(moviesEntity: MoviesEntity, newState: Boolean){
        moviesEntity.isFavorite = newState
        moviesDao.updateMovies(moviesEntity)
    }
}