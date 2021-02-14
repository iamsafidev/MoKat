package id.husni.mokat.core.domain.repository

import androidx.lifecycle.LiveData
import id.husni.mokat.core.source.Resources
import id.husni.mokat.core.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    fun getAllMovies(): Flow<Resources<List<Movies>>>

    fun getFavouriteMovies(): Flow<List<Movies>>

    fun setFavouriteMovies(movies: Movies, newState: Boolean)
}