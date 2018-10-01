package developer.com.core.data.source

import developer.com.core.extension.weak
import java.lang.ref.WeakReference

abstract class DataSource<T> : SubscribableDataSource<T> {

    private var subscriber: WeakReference<SubscribableDataSource.Callback>? = null

    override val isEmpty: Boolean get() = itemCount == 0

    override fun subscribe(callback: SubscribableDataSource.Callback) {
        subscriber = callback.weak()
    }

    protected fun notifyDataLoaded() {
        subscriber?.get()?.onDataLoaded()
    }

    protected fun notifyDataChanged() {
        subscriber?.get()?.onDataChanged()
    }

    protected fun notifyDataNotAvailable(error: String? = null) {
        subscriber?.get()?.onDataNotAvailable(error)
    }
}