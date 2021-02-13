package id.husni.mokat.detail

import androidx.lifecycle.ViewModel
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.usecase.MoviesUseCase

class DetailMoviesViewModel(private val moviesUseCase: MoviesUseCase): ViewModel() {
    fun setFavourite(movies: Movies, state: Boolean) = moviesUseCase.setFavoriteMovies(movies,state)
}