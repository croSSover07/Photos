package developer.com.core.presentation.base.adapter.delegate

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class AdapterDelegatesManager<in T>(private vararg val delegates: AdapterDelegate<T>) {

    fun getItemViewType(position: Int, item: T, payload: Int) = delegates.indexOfFirst {
        it.isForViewType(position, item, payload)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegates.elementAtOrElse(viewType) {
        error("No AdapterDelegates registered for view type: $viewType.")
    }.onCreateViewHolder(parent)

    open fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: T?) = delegates
        .elementAtOrNull(holder.itemViewType)?.onBindViewHolder(position, holder, item) ?: Unit
}