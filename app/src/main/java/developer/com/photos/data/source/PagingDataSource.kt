package developer.com.photos.data.source

import android.arch.paging.PositionalDataSource
import developer.com.core.data.net.ServerCallback
import developer.com.core.data.net.ServerError
import developer.com.core.data.source.SubscribableDataSource
import developer.com.core.extension.weak
import retrofit2.Call
import java.lang.ref.WeakReference

abstract class PagingDataSource<T> : PositionalDataSource<T>() {

    companion object {
        private const val START_PAGE = 1
    }

    abstract fun request(): Call<Array<T>>

    private var subscriber: WeakReference<SubscribableDataSource.Callback>? = null
    private var loadInit: WeakReference<LoadInitialCallback<T>>? = null
    private var loadRange: WeakReference<LoadRangeCallback<T>>? = null
    protected var page: Int = 0

    fun subscribe(callback: SubscribableDataSource.Callback) {
        subscriber = callback.weak()
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        page += 1
        loadRange = callback.weak()
        request().enqueue(callBackRange)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        page = START_PAGE
        loadInit = callback.weak()
        request().enqueue(callBackInit)
    }

    private val callBackInit = object : ServerCallback<Array<T>>() {
        override fun onSuccess(response: Array<T>) {
            loadInit?.get()?.onResult(response.toList(), 0)
        }

        override fun onFailure(error: ServerError?) = Unit
    }

    private val callBackRange = object : ServerCallback<Array<T>>() {
        override fun onSuccess(response: Array<T>) {
            loadRange?.get()?.onResult(response.toList())
        }

        override fun onFailure(error: ServerError?) = Unit
    }
}