package id.husni.mokat.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.local.entity.MoviesEntity

class FavoriteViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    fun getAllFavorite(): LiveData<List<MoviesEntity>> = moviesRepository.getFavouriteMovies()
}