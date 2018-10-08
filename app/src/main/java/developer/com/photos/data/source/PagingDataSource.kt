package developer.com.photos.data.source

import android.arch.paging.PageKeyedDataSource
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import ru.gildor.coroutines.retrofit.await

abstract class PagingDataSource<T> : PageKeyedDataSource<Int, T>() {
    abstract fun request(page: Int, perPage: Int): Call<List<T>>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) = runBlocking {
        val list = request(1, params.requestedLoadSize).await()
        callback.onResult(list, null, 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) = runBlocking {
        val list = request(params.key, params.requestedLoadSize).await()
        callback.onResult(list, params.key.inc())
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) = runBlocking {
        val list = request(params.key, params.requestedLoadSize).await()
        callback.onResult(list, params.key.dec())
    }
}