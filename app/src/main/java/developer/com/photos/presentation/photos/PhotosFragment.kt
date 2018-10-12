package developer.com.photos.presentation.photos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import developer.com.core.presentation.base.BaseListFragment
import developer.com.core.util.Constant
import developer.com.photos.R
import developer.com.photos.data.model.Photo
import developer.com.photos.di.PhotosModule
import developer.com.photos.presentation.base.PagingAdapter
import toothpick.Scope
import javax.inject.Inject

class PhotosFragment : BaseListFragment<PagingAdapter<Photo>>(), PhotosContract.View,
    SearchView.OnQueryTextListener {

    override val toolbarTitleRes = R.string.photos
    override val optionsMenuRes = R.menu.menu_photo

    @Inject lateinit var presenter: PhotosContract.Presenter
    @Inject lateinit var factory: PhotosDataSource.Factory

    lateinit var pagedList: LiveData<PagedList<Photo>>

    override fun installModules(scope: Scope) = scope.installModules(PhotosModule(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constant.ITEM_PER_PAGE)
            .build()

        pagedList = LivePagedListBuilder(factory, config).build()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchView: SearchView = menu.findItem(R.id.itemSearch).actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onViewHolderClick(viewHolder: RecyclerView.ViewHolder, position: Int, id: Int) {
        presenter.navigateTo(position)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        factory.search(query)
        pagedList.value?.dataSource?.invalidate()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText?.length == 0) {
            factory.search(null)
            pagedList.value?.dataSource?.invalidate()
            return true
        }
        return false
    }
}