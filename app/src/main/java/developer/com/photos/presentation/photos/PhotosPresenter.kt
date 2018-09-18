package developer.com.photos.presentation.photos

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
    private val api: Api,
    private val job: Job
) : Presenter<PhotosContract.View>(v), PhotosContract.Presenter {
    override val provider = MutableListProvider<Photo>()

    private val executor = AppExecutor()

    override fun start() {
        request()
    }

    override fun refresh() {
        request()
    }

    override fun navigateTo(position: Int) {
        router.navigateTo(Screen.Main.Photo(provider[position]?.id ?: return))
    }

    private fun request() {
        launch(executor.ui, parent = job) {
            try {
                view?.isRefreshing = true
                provider.addAll(api.photos().await(), true)
                view?.update()
            } catch (e: HttpException) {
                router.showSystemMessage(e.message)
            } finally {
                view?.isRefreshing = false
            }
        }
    }
}