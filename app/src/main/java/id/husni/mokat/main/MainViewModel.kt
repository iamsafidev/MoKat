package id.husni.mokat.main

import androidx.lifecycle.ViewModel
import id.husni.mokat.core.data.source.MoviesRepository

class MainViewModel(moviesRepository: MoviesRepository): ViewModel() {
    val getAllMovies = moviesRepository.getAllMovies()
}