package id.husni.mokat.detail

import androidx.lifecycle.ViewModel
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.usecase.MoviesUseCase

class DetailMoviesViewModel(private val moviesUseCase: MoviesUseCase): ViewModel() {
    fun setFavourite(movies: Movies, newStatus: Boolean) = moviesUseCase.setFavoriteMovies(movies,newStatus)
}