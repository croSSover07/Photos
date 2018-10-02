package developer.com.photos.data.source

import android.arch.paging.PositionalDataSource
import developer.com.core.data.net.ServerCallback
import developer.com.core.data.net.ServerError
import developer.com.core.data.source.SubscribableDataSource
import developer.com.core.extension.weak
import developer.com.core.presentation.base.provider.GetableProvider
import retrofit2.Call
import java.lang.ref.WeakReference

abstract class PagingDataSource<T> : PositionalDataSource<T>(), GetableProvider<T> {

    companion object {
        private const val START_PAGE = 1
    }

    abstract fun request(): Call<Array<T>>

    protected var page: Int = START_PAGE
    protected var data: MutableList<T>? = null

    private var subscriber: WeakReference<SubscribableDataSource.Callback>? = null

    private var loadInitialCallback: WeakReference<LoadInitialCallback<T>>? = null
    private var loadRangeCallback: WeakReference<LoadRangeCallback<T>>? = null

    fun subscribe(callback: SubscribableDataSource.Callback) {
        subscriber = callback.weak()
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        data = mutableListOf()
        page = START_PAGE
        loadInitialCallback = callback.weak()
        request().enqueue(callBackInit)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        page += 1
        loadRangeCallback = callback.weak()
        request().enqueue(callBackRange)
    }

    override fun get(position: Int) = data?.getOrNull(position)

    private fun notifyDataLoaded() {
        subscriber?.get()?.onDataLoaded()
    }

    private val callBackInit = object : ServerCallback<Array<T>>() {
        override fun onSuccess(response: Array<T>) {
            loadInitialCallback?.get()?.onResult(response.toList(), 0)
            data = response.toMutableList()
            notifyDataLoaded()
        }

        override fun onFailure(error: ServerError?) = Unit
    }

    private val callBackRange = object : ServerCallback<Array<T>>() {
        override fun onSuccess(response: Array<T>) {
            data?.addAll(response)
            loadRangeCallback?.get()?.onResult(response.toList())
            notifyDataLoaded()
        }

        override fun onFailure(error: ServerError?) = Unit
    }
}