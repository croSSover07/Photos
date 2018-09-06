package developer.com.core.presentation.base.provider

open class MutableListProvider<T>(
    protected val list: MutableList<T> = mutableListOf()
) : GetableProvider<T> {

    override val itemCount: Int get() = list.size
    override fun get(at: Int): T? = list.getOrNull(at)

    fun asList(): List<T> = list
    fun asMutableList() = list

    fun add(item: T): Int {
        val position = list.size
        list.add(item)
        return position
    }

    fun insert(item: T, index: Int = 0) {
        list.add(index, item)
    }

    operator fun set(index: Int, item: T) {
        if (index < itemCount) {
            list[index] = item
        } else list.add(index, item)
    }

    fun remove(item: T): Int {
        val position = list.size
        list.remove(item)
        return position
    }

    fun find(predicate: (T) -> Boolean): T? = list.find(predicate)

    fun removeAt(index: Int) = list.removeAt(index)

    fun addAll(items: Collection<T>, clear: Boolean = false) {
        if (clear) list.clear()
        list.addAll(items)
    }

    fun addAll(items: Array<T>, clear: Boolean = false) {
        if (clear) list.clear()
        list.addAll(items)
    }

    fun clear() = list.clear()
}