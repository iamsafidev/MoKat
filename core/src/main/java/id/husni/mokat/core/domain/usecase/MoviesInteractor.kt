package id.husni.mokat.core.domain.usecase

import androidx.lifecycle.LiveData
import id.husni.mokat.core.source.Resources
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val moviesRepository: IMoviesRepository): MoviesUseCase {
    override fun getAllMovies(): Flow<Resources<List<Movies>>>  = moviesRepository.getAllMovies()

    override fun getFavoriteMovies(): Flow<List<Movies>> = moviesRepository.getFavouriteMovies()

    override fun setFavoriteMovies(movies: Movies, state: Boolean) = moviesRepository.setFavouriteMovies(movies,state)

}