package developer.com.core.presentation.base.provider

open class MutableArrayProvider<T>(protected var array: Array<T>) : Provider<T> {
    private val items: Array<T> get() = array

    override val itemCount: Int get() = items.size
    override fun get(index: Int): T? = items.getOrNull(index)

    fun update(new: Array<T>) {
        array = new
    }
}