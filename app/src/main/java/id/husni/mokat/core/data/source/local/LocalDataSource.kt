package id.husni.mokat.core.data.source.local

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.local.room.MoviesDao

class LocalDataSource private constructor(private val moviesDao: MoviesDao){
    companion object{
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao): LocalDataSource =
            instance ?: synchronized(this){
                instance ?: LocalDataSource(moviesDao)
            }
    }

    fun getAllMovies() : LiveData<List<MoviesEntity>> = moviesDao.getAllMovies()

    fun getFavorite(): LiveData<List<MoviesEntity>> = moviesDao.getFavorite()

    fun insertMovies(movies: List<MoviesEntity>) = moviesDao.insertMovies(movies)

    fun setFavorite(moviesEntity: MoviesEntity, newState: Boolean){
        moviesEntity.isFavorite = newState
        moviesDao.updateMovies(moviesEntity)
    }
}