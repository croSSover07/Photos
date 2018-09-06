package developer.com.core.presentation.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegate
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegatesManager
import developer.com.core.presentation.base.provider.Provider

open class RecyclerAdapter<out P : Provider>(
    delegates: List<AdapterDelegate>,
    protected val provider: P
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    open val manager = AdapterDelegatesManager(delegates)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        manager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        manager.onBindViewHolder(holder, position)

    override fun getItemViewType(position: Int) = manager.getItemViewType(position, 0)
    override fun getItemCount() = provider.itemCount
}