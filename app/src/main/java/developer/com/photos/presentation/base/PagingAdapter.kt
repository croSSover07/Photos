package developer.com.photos.presentation.base

import android.arch.paging.PagedListAdapter
import android.os.Handler
import android.os.Looper
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegate
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegatesManager
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.photos.data.model.WithIdentifier
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class PagingAdapter<T : WithIdentifier>(
    vararg delegates: AdapterDelegate<T>
) : PagedListAdapter<T, RecyclerView.ViewHolder>(ItemCallback()), GetableProvider<T> {

    private val manager = AdapterDelegatesManager(*delegates)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        manager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        manager.onBindViewHolder(holder, position, getItem(position))

    override fun getItemViewType(position: Int): Int {
        return manager.getItemViewType(position, getItem(position) ?: return 0, 0)
    }

    override fun get(position: Int): T? = getItem(position)
}

class ItemCallback<T : WithIdentifier> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}