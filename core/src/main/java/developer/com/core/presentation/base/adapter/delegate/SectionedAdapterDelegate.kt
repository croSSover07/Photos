package developer.com.core.presentation.base.adapter.delegate

import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.core.presentation.base.provider.section.ItemPosition
import developer.com.core.presentation.base.provider.section.SectionedProvider

abstract class SectionedAdapterDelegate<S, I, P, L, in V>(
    provider: P,
    listener: L?
) : ProviderDelegate<L, V, P>(
    provider,
    listener
) where P : SectionedProvider<S, I>, L : ViewHolder.OnClickListener, V : ViewHolder<L> {

    override fun isForViewType(position: Int, payload: Int, provider: P) =
        isForViewType(provider.getItemPosition(position))

    override fun onBindViewHolder(position: Int, holder: V, provider: P) =
        onBindViewHolder(provider.getItemPosition(position), holder, provider)

    abstract fun onBindViewHolder(position: ItemPosition, holder: V, provider: P)
    abstract fun isForViewType(itemPosition: ItemPosition): Boolean
}