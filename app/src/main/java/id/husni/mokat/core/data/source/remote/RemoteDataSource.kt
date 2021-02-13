package id.husni.mokat.core.data.source.remote

import id.husni.mokat.BuildConfig
import id.husni.mokat.core.data.source.remote.network.ApiResponse
import id.husni.mokat.core.data.source.remote.network.ApiService
import id.husni.mokat.core.data.source.remote.response.MoviesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(apiService)
            }
    }

    suspend fun getAllMovies(): Flow<ApiResponse<List<MoviesItem>>>{
        return flow {
            try {
                val client = apiService.getAllMovies(BuildConfig.TMDB_API,"en-US")
                val dataArray = client.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                }
                else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}