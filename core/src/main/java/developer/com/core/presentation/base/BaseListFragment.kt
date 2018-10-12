package developer.com.core.presentation.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import developer.com.core.R
import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.core.presentation.view.Updatable

abstract class BaseListFragment<A> : BaseFragment(),
    Updatable,
    ViewHolder.OnClickListener where A : RecyclerView.Adapter<*> {
    override val layoutResId = R.layout.fragment_list

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var adapter: A

    protected abstract fun createAdapter(): A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = createAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        configure(context ?: return, recyclerView)
    }

    protected open fun createLayoutManager(context: Context): RecyclerView.LayoutManager =
        LinearLayoutManager(context)

    @CallSuper
    protected open fun configure(context: Context, recyclerView: RecyclerView) {
        recyclerView.layoutManager = createLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onViewHolderClick(
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
        id: Int
    ) = Unit

    //region Updatable

    override fun update() = adapter.notifyDataSetChanged()
    override fun onItemChanged(start: Int, count: Int) =
        adapter.notifyItemRangeChanged(start, count)

    override fun onItemInserted(position: Int, count: Int) =
        adapter.notifyItemRangeInserted(position, count)

    override fun onItemRemoved(position: Int, count: Int) =
        adapter.notifyItemRangeRemoved(position, count)

    //endregion
}