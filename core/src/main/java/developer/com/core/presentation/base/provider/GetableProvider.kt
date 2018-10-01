package developer.com.core.presentation.base.provider

interface GetableProvider<out T> {
    fun get(position: Int): T?
}