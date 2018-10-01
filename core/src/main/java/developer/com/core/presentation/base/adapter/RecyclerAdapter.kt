package developer.com.core.presentation.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegate
import developer.com.core.presentation.base.adapter.delegate.AdapterDelegatesManager
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.core.presentation.base.provider.Provider

open class RecyclerAdapter<T, out P>(
    protected val provider: P,
    vararg delegates: AdapterDelegate<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), GetableProvider<T> where P : Provider<T> {

    protected open val manager = AdapterDelegatesManager(*delegates)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        manager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        manager.onBindViewHolder(holder, position, provider[position])
    }

    override fun getItemViewType(position: Int): Int {
        val item = provider[position] ?: return 0
        return manager.getItemViewType(position, item, 0)
    }

    override fun getItemCount() = provider.itemCount
    override fun get(position: Int): T? = provider[position]
}