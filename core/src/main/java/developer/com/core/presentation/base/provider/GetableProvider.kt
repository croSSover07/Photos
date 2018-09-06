package developer.com.core.presentation.base.provider

interface GetableProvider<T> : Provider {
    operator fun get(at: Int): T?
}