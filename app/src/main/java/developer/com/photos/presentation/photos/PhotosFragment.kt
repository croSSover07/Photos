package developer.com.photos.presentation.photos

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import developer.com.core.presentation.base.BaseListFragment
import developer.com.core.presentation.util.Constant
import developer.com.photos.R
import developer.com.photos.data.model.Photo
import developer.com.photos.di.PhotosModule
import developer.com.photos.presentation.base.PagingAdapter
import toothpick.Scope
import javax.inject.Inject

class PhotosFragment : BaseListFragment<PagingAdapter<Photo>>(), PhotosContract.View {

    override val toolbarTitleRes = R.string.photos

    @Inject lateinit var presenter: PhotosContract.Presenter
    @Inject lateinit var factory: PhotosDataSource.Factory

    override fun installModules(scope: Scope) = scope.installModules(PhotosModule(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constant.ITEM_PER_PAGE)
            .build()

        val pagedList = LivePagedListBuilder(factory, config).build()
        pagedList.observe(this, Observer { adapter.submitList(it) })

        presenter.attach(adapter)
        presenter.start()
    }

    override fun createAdapter() = PagingAdapter(
        PhotoAdapterDelegate(this)
    )

    override fun configure(context: Context, recyclerView: RecyclerView) {
        super.configure(context, recyclerView)
        recyclerView.setHasFixedSize(false)
    }

    override fun onViewHolderClick(viewHolder: RecyclerView.ViewHolder, position: Int, id: Int) {
        presenter.navigateTo(position)
    }
}