package id.husni.mokat.core.domain.usecase

import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getAllMovies(): Flow<Resources<List<Movies>>>
    fun getFavoriteMovies(): Flow<List<Movies>>
    fun setFavoriteMovies(movies: Movies,state: Boolean)

}