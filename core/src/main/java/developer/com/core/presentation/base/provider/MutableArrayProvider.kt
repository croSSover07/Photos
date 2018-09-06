package developer.com.core.presentation.base.provider

open class MutableArrayProvider<T>(protected var array: Array<T>) : GetableProvider<T> {
    override val itemCount: Int get() = array.size
    override fun get(at: Int): T? = array.getOrNull(at)

    fun update(array: Array<T>) {
        this.array = array
    }
}