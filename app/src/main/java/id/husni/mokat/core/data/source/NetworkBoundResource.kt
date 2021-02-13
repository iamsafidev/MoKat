package id.husni.mokat.core.data.source

import id.husni.mokat.core.data.source.remote.network.ApiResponse
import id.husni.mokat.core.utils.AppExecutors
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType>{

    private var result: Flow<Resources<ResultType>> = flow {
        emit(Resources.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resources.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resources.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resources.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resources.Error<ResultType>(apiResponse.message))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resources.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resources<ResultType>> = result
}

