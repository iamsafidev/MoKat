package id.husni.mokat.detail

import androidx.lifecycle.ViewModel
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.local.entity.MoviesEntity

class DetailMoviesViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    fun setFavourite(moviesEntity: MoviesEntity, state: Boolean) = moviesRepository.setFavouriteMovies(moviesEntity,state)
}