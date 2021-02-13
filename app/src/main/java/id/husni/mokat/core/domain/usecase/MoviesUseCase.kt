package id.husni.mokat.core.domain.usecase

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.domain.model.Movies

interface MoviesUseCase {
    fun getAllMovies(): LiveData<Resources<List<Movies>>>
    fun getFavoriteMovies(): LiveData<List<Movies>>
    fun setFavoriteMovies(movies: Movies,state: Boolean)

}