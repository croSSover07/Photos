package developer.com.core.presentation.base.provider

interface Provider<out T> {
    val itemCount: Int
    val isEmpty: Boolean get() = itemCount == 0
    operator fun get(index: Int): T?
}