package developer.com.photos.data.source

import android.arch.paging.PageKeyedDataSource
import kotlinx.coroutines.experimental.runBlocking

abstract class PagingDataSource<T> : PageKeyedDataSource<Int, T>() {
    abstract suspend fun process(page: Int, perPage: Int): List<T>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) = runBlocking {
        val list = process(1, params.requestedLoadSize)
        callback.onResult(list, null, 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) =
        runBlocking {
            val list = process(params.key, params.requestedLoadSize)
            callback.onResult(list, params.key.inc())
        }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) = runBlocking {
        val list = process(params.key, params.requestedLoadSize)
        callback.onResult(list, params.key.dec())
    }
}