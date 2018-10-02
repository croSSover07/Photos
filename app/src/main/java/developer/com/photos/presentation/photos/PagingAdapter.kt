package developer.com.photos.presentation.photos

import android.arch.paging.PagedListAdapter
import android.os.Handler
import android.os.Looper
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegate
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegatesManager
import developer.com.photos.data.model.WithIdentifier
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class PagingAdapter<T : WithIdentifier>(
    vararg delegates: AdapterDelegate<T>
) : PagedListAdapter<T, RecyclerView.ViewHolder>(ItemCallback()) {

    private val manager = AdapterDelegatesManager(*delegates)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        manager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        manager.onBindViewHolder(holder, position, getItem(position))

    override fun getItemViewType(position: Int): Int {
        return manager.getItemViewType(position, getItem(position) ?: return 0, 0)
    }
}

class ItemCallback<T : WithIdentifier> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}

// Ui thread executor to execute runnable on UI thread
internal class UiThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        handler.post(command)
    }
}

// Background thread executor to execute runnable on background thread
internal class BackgroundThreadExecutor : Executor {
    private val executorService = Executors.newFixedThreadPool(2)

    override fun execute(command: Runnable) {
        executorService.execute(command)
    }
}