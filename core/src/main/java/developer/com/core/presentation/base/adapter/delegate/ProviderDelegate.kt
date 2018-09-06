package developer.com.core.presentation.base.adapter.delegate

import android.support.v7.widget.RecyclerView
import developer.com.core.extension.weak
import developer.com.core.presentation.base.adapter.ViewHolder
import java.lang.ref.WeakReference

abstract class ProviderDelegate<L, in V : ViewHolder<L>, P>(val provider: P, listener: L? = null) :
    AdapterDelegate {

    protected val listener: WeakReference<L>? = listener?.weak()

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(position: Int, holder: RecyclerView.ViewHolder) {
        onBindViewHolder(position, holder as? V ?: return, provider)
    }

    abstract fun onBindViewHolder(position: Int, holder: V, provider: P)

    final override fun isForViewType(position: Int, payload: Int) =
        isForViewType(position, payload, provider)

    open fun isForViewType(position: Int, payload: Int, provider: P): Boolean = true
}