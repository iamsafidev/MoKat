package id.husni.mokat.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.husni.mokat.core.data.source.remote.network.ApiResponse
import id.husni.mokat.core.utils.AppExecutors

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resources<ResultType>>()

    init {
        result.value = Resources.Loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resources.Success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resources.Loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiResponse.Success ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.data)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resources.Success(newData)
                            }
                        }
                    }
                is ApiResponse.Empty -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resources.Success(newData)
                    }
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resources.Error(newData,response.message)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resources<ResultType>> = result
}