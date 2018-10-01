package developer.com.core.presentation.base.provider

open class MutableListProvider<T>(
    protected val list: MutableList<T>
) : Provider<T> {

    constructor() : this(mutableListOf())

    override val itemCount: Int get() = list.size
    override fun get(index: Int): T? = list.getOrNull(index)

    fun add(element: T) = list.add(element)
    fun addAll(elements: Collection<T>) = list.addAll(elements)
    fun addAll(elements: Array<T>) = list.addAll(elements)
    fun insert(element: T, at: Int) = list.add(at, element)
    fun remove(element: T) = list.remove(element)
    fun removeAt(index: Int) = list.removeAt(index)
    fun clear() = list.clear()
}