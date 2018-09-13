package developer.com.core.data.source

import developer.com.core.presentation.base.provider.GetableProvider

abstract class ArrayRepository<T> : Repository<Array<T>, Array<T>>(),
    GetableProvider<T> {

    override val itemCount: Int get() = data?.size ?: 0
    override fun get(at: Int): T? = data?.getOrNull(at)
    override fun process(data: Array<T>) = data

    fun search(predicate: (T) -> Boolean): List<T>? = data?.filter(predicate)
}