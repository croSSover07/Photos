package developer.com.photos.presentation.photos

import developer.com.core.data.source.SubscribableDataSource
import developer.com.core.presentation.base.Presenter
import developer.com.core.presentation.base.flow.FlowRouter
import developer.com.photos.Screen
import developer.com.photos.data.net.Api
import javax.inject.Inject

class PhotosPresenter @Inject constructor(
    v: PhotosContract.View,
    private val router: FlowRouter,
    api: Api
) : Presenter<PhotosContract.View>(v), PhotosContract.Presenter, SubscribableDataSource.Callback {
    override val provider = PhotosDataSource(api)

    init {
        provider.subscribe(this)
    }

    override fun start() = Unit
    override fun refresh() = Unit

    override fun navigateTo(position: Int) {
        router.navigateTo(Screen.Main.Photo(provider.get(position)?.id ?: return))
    }

    override fun onDataLoaded() {
        view?.update()
    }

    override fun onDataNotAvailable(error: String?) = Unit
}