package developer.com.core.presentation.base.adapter.delegate

import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.core.presentation.base.provider.GetableProvider

abstract class TypedProviderDelegate<T, L, in V : ViewHolder<L>>(
    listener: L?
) : ProviderDelegate<T, L, V>(listener) {
    override fun onBindViewHolder(position: Int, holder: V, item: T) {
        onBindViewHolder(position, holder, item ?: return)
    }

    abstract fun onBindViewHolder(position: Int, holder: V, provider: GetableProvider<T>, item: T)
}