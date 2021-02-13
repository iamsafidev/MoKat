package id.husni.mokat.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.usecase.MoviesUseCase

class FavoriteViewModel(moviesUseCase: MoviesUseCase): ViewModel() {
    val getAllFavorite = moviesUseCase.getFavoriteMovies()
}