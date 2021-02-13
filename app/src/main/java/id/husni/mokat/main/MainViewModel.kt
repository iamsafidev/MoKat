package id.husni.mokat.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.domain.usecase.MoviesUseCase

class MainViewModel( moviesUseCase: MoviesUseCase): ViewModel() {
    val getAllMovies = moviesUseCase.getAllMovies().asLiveData()
}