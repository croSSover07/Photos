package developer.com.core.presentation.base.adapter.delegate

import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.core.presentation.base.provider.GetableProvider

abstract class TypedProviderDelegate<T, L, in V : ViewHolder<L>>(
    provider: GetableProvider<T>,
    listener: L?
) : ProviderDelegate<L, V, GetableProvider<T>>(provider, listener) {
    override fun onBindViewHolder(position: Int, holder: V, provider: GetableProvider<T>) {
        onBindViewHolder(position, holder, provider, provider[position] ?: return)
    }

    abstract fun onBindViewHolder(position: Int, holder: V, provider: GetableProvider<T>, item: T)
}