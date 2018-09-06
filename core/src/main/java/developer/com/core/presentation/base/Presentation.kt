package developer.com.core.presentation.base

import developer.com.core.data.source.Repository

// region View
interface Attachable {
    val isAttached: Boolean
}

interface OnBackPressedListener {
    /** @return TRUE if handled by listener, FALSE otherwise */
    fun onBackPressed(): Boolean
}

interface Updatable : Attachable {
    fun update()
    fun onItemInserted(position: Int, count: Int = 1)
    fun onItemRemoved(position: Int, count: Int = 1)
    fun onItemChanged(start: Int, count: Int)
}

interface Refreshable : Attachable {
    var isRefreshing: Boolean
}
// endregion

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