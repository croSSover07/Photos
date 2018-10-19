package developer.com.photos.presentation.photos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
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

    companion object {
        private const val EXTRA_0 = "pf.extra.0" // query
        const val SPAN_COUNT = 3
    }

    override val toolbarTitleRes = R.string.photos
    override val optionsMenuRes = R.menu.menu_photo

    @Inject lateinit var presenter: PhotosContract.Presenter
    @Inject lateinit var factory: PhotosDataSource.Factory

    lateinit var pagedList: LiveData<PagedList<Photo>>
    private var query: String? = null

    override fun installModules(scope: Scope) = scope.installModules(PhotosModule(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        query = savedInstanceState?.getString(EXTRA_0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constant.ITEM_PER_PAGE)
            .build()

        pagedList = LivePagedListBuilder(factory, config).build()
        query?.let { search(it) }
        pagedList.observe(this, Observer { adapter.submitList(it) })

        presenter.attach(adapter)
    }

    override fun createAdapter() = PagingAdapter(
        PhotoAdapterDelegate(this)
    )

    override fun configure(context: Context, recyclerView: RecyclerView) {
        super.configure(context, recyclerView)
        recyclerView.setHasFixedSize(false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val searchItem = menu.findItem(R.id.itemSearch)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.also { sv ->
            sv.maxWidth = Int.MAX_VALUE
            sv.setOnQueryTextListener(this@PhotosFragment)

            query?.let { q ->
                searchItem.expandActionView()
                sv.setQuery(q, true)
            }
        }
    }

    override fun onViewHolderClick(viewHolder: RecyclerView.ViewHolder, position: Int, id: Int) {
        presenter.navigateTo(position)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!isVisible) return true

        if (newText?.length == 0) {
            search(null)
            return true
        }
        return false
    }

    private fun search(q: String?) {
        query = q
        factory.search(query)
        pagedList.value?.dataSource?.invalidate()
    }

    override fun createLayoutManager(context: Context) =
        GridLayoutManager(context, SPAN_COUNT).apply {
            spanSizeLookup = lookup
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_0, query)
    }

    private val lookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            if (position % 3 == 2) return 3

            return when (position % 4) {
                1, 3 -> 1
                0, 2 -> 2
                else -> 1
            }
        }
    }
}