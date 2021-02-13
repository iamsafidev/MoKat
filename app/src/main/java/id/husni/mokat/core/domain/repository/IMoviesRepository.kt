package id.husni.mokat.core.domain.repository

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.domain.model.Movies

interface IMoviesRepository {
    fun getAllMovies(): LiveData<Resources<List<Movies>>>

    fun getFavouriteMovies(): LiveData<List<Movies>>

    fun setFavouriteMovies(movies: Movies, newState: Boolean)
}