package developer.com.core.presentation.base.adapter.delegate

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface AdapterDelegate<in T> {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(position: Int, holder: RecyclerView.ViewHolder, item: T?)
    fun isForViewType(position: Int, item: T?, payload: Int): Boolean
}