package id.husni.mokat.di

import id.husni.mokat.core.domain.usecase.MoviesInteractor
import id.husni.mokat.core.domain.usecase.MoviesUseCase
import id.husni.mokat.detail.DetailMoviesViewModel
import id.husni.mokat.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MoviesUseCase> { MoviesInteractor(get()) }

}
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailMoviesViewModel(get()) }
}