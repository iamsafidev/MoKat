package id.husni.mokat.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.husni.mokat.BuildConfig
import id.husni.mokat.core.data.source.remote.network.ApiResponse
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

    fun getAllMovies(): LiveData<ApiResponse<List<MoviesItem>>>{
        val results = MutableLiveData<ApiResponse<List<MoviesItem>>>()

        val client = apiService.getAllMovies(BuildConfig.TMDB_API,"en_US")
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val dataArray = response.body()?.results
                results.value = if (dataArray !=null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                results.value = ApiResponse.Error(t.message.toString())
            }
        })
        return results
    }
}