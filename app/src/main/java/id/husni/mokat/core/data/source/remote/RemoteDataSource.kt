package id.husni.mokat.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.husni.mokat.BuildConfig
import id.husni.mokat.core.data.source.remote.network.ApiService
import id.husni.mokat.core.data.source.remote.response.MovieResponse
import id.husni.mokat.core.data.source.remote.response.MoviesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(apiService)
            }
    }

    fun getAllMovies(): LiveData<List<MoviesItem>>{
        val results = MutableLiveData<List<MoviesItem>>()

        val client = apiService.getAllMovies(BuildConfig.TMDB_API,"en_US")
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                results.value = response.body()?.results
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })
        return results
    }
}