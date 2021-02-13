package id.husni.mokat.core.domain.usecase

import androidx.lifecycle.LiveData
import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.repository.IMoviesRepository

class MoviesInteractor(private val moviesRepository: IMoviesRepository): MoviesUseCase {
    override fun getAllMovies(): LiveData<Resources<List<Movies>>>  = moviesRepository.getAllMovies()

    override fun getFavoriteMovies(): LiveData<List<Movies>> = moviesRepository.getFavouriteMovies()

    override fun setFavoriteMovies(movies: Movies, state: Boolean) = moviesRepository.setFavouriteMovies(movies,state)

}