package developer.com.core.presentation.presenter

import developer.com.core.data.source.Repository

// region Presenter
interface Refreshing {
    fun refresh()
}

interface Starting {
    fun start()
}

interface Detaching {
    fun detach()
}

interface HasOptionalRepository {
    val repository: Repository<*, *>?
}
// endregion