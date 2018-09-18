package developer.com.photos.presentation.photos

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import developer.com.core.presentation.base.BaseListFragment
import developer.com.core.presentation.base.adapter.RecyclerAdapter
import developer.com.photos.R
import developer.com.photos.di.PhotosModule
import toothpick.Scope
import javax.inject.Inject

class PhotosFragment : BaseListFragment(), PhotosContract.View,
    SwipeRefreshLayout.OnRefreshListener {

    override val toolbarTitleRes = R.string.photos

    @Inject lateinit var presenter: PhotosContract.Presenter

    override fun installModules(scope: Scope) = scope.installModules(PhotosModule(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun createAdapter() = RecyclerAdapter(
        listOf(PhotoAdapterDelegate(presenter.provider, this)),
        presenter.provider
    )

    override fun onRefresh() {
        presenter.refresh()
    }

    override fun onViewHolderClick(viewHolder: RecyclerView.ViewHolder, position: Int, id: Int) {
        presenter.navigateTo(position)
    }
}