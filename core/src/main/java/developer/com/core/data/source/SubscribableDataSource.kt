package developer.com.core.data.source

import developer.com.core.presentation.base.provider.Provider

interface SubscribableDataSource<T> : Provider<T> {
    interface Callback {
        fun onDataLoaded()
        fun onDataNotAvailable(error: String?)
        fun onDataChanged() = Unit
    }

    fun load(force: Boolean = false)
    fun subscribe(callback: Callback)
}