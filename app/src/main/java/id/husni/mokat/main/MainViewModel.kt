package id.husni.mokat.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.husni.mokat.core.domain.usecase.MoviesUseCase

class MainViewModel( moviesUseCase: MoviesUseCase): ViewModel() {
    val getAllMovies = moviesUseCase.getAllMovies().asLiveData()
}