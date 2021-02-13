package id.husni.mokat.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.husni.mokat.core.data.source.MoviesRepository
import id.husni.mokat.core.di.Injection
import id.husni.mokat.core.domain.usecase.MoviesUseCase
import id.husni.mokat.detail.DetailMoviesViewModel
import id.husni.mokat.favorite.FavoriteViewModel
import id.husni.mokat.main.MainViewModel

class ViewModelFactory private constructor(private val moviesUseCase: MoviesUseCase): ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideMoviesUseCase(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->{
                MainViewModel(moviesUseCase) as T
            }
            modelClass.isAssignableFrom(DetailMoviesViewModel::class.java)->{
                DetailMoviesViewModel(moviesUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->{
                FavoriteViewModel(moviesUseCase) as T
            }
            else -> throw Throwable(modelClass.name)
        }
    }
}