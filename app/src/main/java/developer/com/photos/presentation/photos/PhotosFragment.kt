package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.BaseListFragment
import developer.com.core.presentation.base.adapter.RecyclerAdapter
import developer.com.photos.R
import developer.com.photos.di.PhotosModule
import toothpick.Scope
import javax.inject.Inject

class PhotosFragment : BaseListFragment(), PhotosContract.View {

    override val toolbarTitleRes = R.string.photos

    @Inject lateinit var presenter: PhotosContract.Presenter

    override fun installModules(scope: Scope)  = scope.installModules(PhotosModule(this))

    override fun createAdapter() = RecyclerAdapter(
        listOf(PhotoAdapterDelegate(presenter.provider, this)),
        presenter.provider
    )
}