package id.husni.mokat.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavorite(): Flow<List<MoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>)

    @Update
    fun updateMovies(moviesEntity: MoviesEntity)
}