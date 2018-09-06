package developer.com.core.data.source

import developer.com.core.presentation.base.provider.Provider

interface SubscribableDataSource : Provider {
    interface Callback {
        fun onDataLoaded()
        fun onDataNotAvailable(error: String?)
        fun onDataChanged() = Unit
    }

    fun load(force: Boolean = false)
    fun subscribe(callback: Callback)
}