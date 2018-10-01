package developer.com.core.presentation.base.adapter.delegate

import android.support.v7.widget.RecyclerView
import developer.com.core.extension.weak
import developer.com.core.presentation.base.adapter.ViewHolder
import java.lang.ref.WeakReference

abstract class ProviderDelegate<in ITEM, LISTENER, in HOLDER>(listener: LISTENER? = null) :
    AdapterDelegate<ITEM> where HOLDER : ViewHolder<LISTENER> {

    protected val listener: WeakReference<LISTENER>? = listener?.weak()

    abstract fun onBindViewHolder(position: Int, holder: HOLDER, item: ITEM)

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(
        position: Int, holder: RecyclerView.ViewHolder, item: ITEM?
    ) {
        val h = holder as? HOLDER ?: return
        if (item != null) onBindViewHolder(position, h, item) else onBindPlaceholder(position, h)
    }

    open fun onBindPlaceholder(position: Int, holder: HOLDER) = Unit
    override fun isForViewType(position: Int, item: ITEM?, payload: Int) = true
}