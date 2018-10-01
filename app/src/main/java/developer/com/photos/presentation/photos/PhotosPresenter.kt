package developer.com.photos.presentation.photos

import developer.com.core.data.source.SubscribableDataSource
import developer.com.core.presentation.base.Presenter
import developer.com.core.presentation.base.flow.FlowRouter
import developer.com.core.presentation.base.provider.MutableListProvider
import developer.com.photos.Screen
import developer.com.photos.data.model.Photo
import developer.com.photos.data.net.Api
import developer.com.photos.util.AppExecutor
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await
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
        //TODO: add navigate
//        router.navigateTo(Screen.Main.Photo(provider. ?: return))
    }

    override fun onDataLoaded() {
        view?.update()
    }

    override fun onDataNotAvailable(error: String?) = Unit
}