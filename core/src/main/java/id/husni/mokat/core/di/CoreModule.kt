package id.husni.mokat.core.di

import androidx.room.Room
import id.husni.mokat.core.domain.repository.IMoviesRepository
import id.husni.mokat.core.source.MoviesRepository
import id.husni.mokat.core.source.local.LocalDataSource
import id.husni.mokat.core.source.local.room.MoviesDatabase
import id.husni.mokat.core.source.remote.RemoteDataSource
import id.husni.mokat.core.source.remote.network.ApiConfig
import id.husni.mokat.core.source.remote.network.ApiService
import id.husni.mokat.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passPhrase : ByteArray = SQLiteDatabase.getBytes("HUS1287kpod21qwNI".toCharArray())
        val supportFactory = SupportFactory(passPhrase)
        Room.databaseBuilder(androidContext(),MoviesDatabase::class.java,"movies_database.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .openHelperFactory(supportFactory)
            .build()
    }
}
val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname,"sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname,"sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname,"sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(hostname,"sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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