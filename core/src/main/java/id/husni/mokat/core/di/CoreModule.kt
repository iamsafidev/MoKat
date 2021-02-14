package id.husni.mokat.core.di

import androidx.room.Room
import id.husni.mokat.core.source.MoviesRepository
import id.husni.mokat.core.source.local.LocalDataSource
import id.husni.mokat.core.source.local.room.MoviesDatabase
import id.husni.mokat.core.source.remote.RemoteDataSource
import id.husni.mokat.core.source.remote.network.ApiConfig
import id.husni.mokat.core.source.remote.network.ApiService
import id.husni.mokat.core.domain.repository.IMoviesRepository
import id.husni.mokat.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MoviesDatabase>().moviesDao() }
    single {
        Room.databaseBuilder(androidContext(),MoviesDatabase::class.java,"movies_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
         retrofit.create(ApiService::class.java)
    }
}
val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMoviesRepository> {
        MoviesRepository(
            get(),
            get(),
            get()
        )
    }
}