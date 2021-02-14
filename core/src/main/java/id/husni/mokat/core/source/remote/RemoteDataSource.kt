package id.husni.mokat.core.source.remote

import id.husni.mokat.core.source.remote.network.ApiResponse
import id.husni.mokat.core.source.remote.network.ApiService
import id.husni.mokat.core.source.remote.response.MoviesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService){

    suspend fun getAllMovies(): Flow<ApiResponse<List<MoviesItem>>>{
        return flow {
            try {
                val client = apiService.getAllMovies("ea01ca5302449a0f3a35a38947ef180f","en-US")
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