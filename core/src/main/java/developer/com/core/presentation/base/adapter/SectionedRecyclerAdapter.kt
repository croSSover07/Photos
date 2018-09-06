package developer.com.core.presentation.base.adapter

import developer.com.core.presentation.base.adapter.delegate.AdapterDelegate
import developer.com.core.presentation.base.provider.section.SectionedProvider

open class SectionedRecyclerAdapter<S, I>(
    delegates: List<AdapterDelegate>,
    provider: SectionedProvider<S, I>
) : RecyclerAdapter<SectionedProvider<S, I>>(delegates, provider) {

    override fun getItemCount(): Int {
        var count = 0
        for (section in 0 until provider.getSectionsCount()) {
            count += provider.getItemsCountInSection(section)

            if (provider.hasHeaderForSection(section)) count++
            if (provider.hasFooterForSection(section)) count++
        }
        return count
    }
}