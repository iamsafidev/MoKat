package id.husni.mokat.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.husni.mokat.core.domain.usecase.MoviesUseCase

class FavoriteViewModel(moviesUseCase: MoviesUseCase):ViewModel() {
    val getAllFavorite = moviesUseCase.getFavoriteMovies().asLiveData()
}