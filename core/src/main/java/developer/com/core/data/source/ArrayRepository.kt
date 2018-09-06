package developer.com.core.data.source

import developer.com.core.data.net.BaseApi
import developer.com.core.presentation.base.provider.GetableProvider

abstract class ArrayRepository<T>(api: BaseApi) : Repository<Array<T>, Array<T>>(api),
    GetableProvider<T> {

    override val itemCount: Int get() = data?.size ?: 0
    override fun get(at: Int): T? = data?.getOrNull(at)
    override fun process(data: Array<T>) = data

    fun search(predicate: (T) -> Boolean): List<T>? = data?.filter(predicate)
}