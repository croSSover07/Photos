package developer.com.core.data.source

import developer.com.core.data.net.ServerCallback
import developer.com.core.data.net.ServerError
import retrofit2.Call

abstract class Repository<T, R> : DataSource() {

    protected abstract val request: Call<T>

    protected var data: R? = null

    override fun load(force: Boolean) = if (force || isEmpty) {
        request.enqueue(callback)
    } else notifyDataLoaded()

    abstract fun process(data: T): R

    protected open fun onSuccessInternal(response: T) {
        data = process(response)
        notifyDataLoaded()
    }

    protected val callback = object : ServerCallback<T>() {
        override fun onSuccess(response: T) = onSuccessInternal(response)
        override fun onFailure(error: ServerError?) = notifyDataNotAvailable(error?.message)
    }
}