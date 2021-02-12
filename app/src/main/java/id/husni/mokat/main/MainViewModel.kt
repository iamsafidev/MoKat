package id.husni.mokat.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.data.source.local.entity.MoviesEntity

class MainViewModel(private val moviesRepository: MoviesRepository): ViewModel() {
    fun getAllMovies(): LiveData<Resources<List<MoviesEntity>>> = moviesRepository.getAllMovies()
}