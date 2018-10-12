package developer.com.core.presentation.view

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

interface Titleable {
    fun updateTitle(title: String)
}

interface Messageable {
    /** @param withDuration default value is 0 (Toast.LENGTH_SHORT / Snackbar.LENGTH_LONG) */
    fun show(message: String, withDuration: Int = 0, param: Int = 0)
}
// endregion