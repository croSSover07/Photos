package developer.com.photos.presentation.photo

import developer.com.core.presentation.base.Presenter
import developer.com.photos.data.model.Photo
import developer.com.photos.data.net.Api
import developer.com.photos.di.qualifier.PhotoId
import developer.com.photos.util.AppExecutor
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PhotoPresenter @Inject constructor(
    v: PhotoContract.View,
    @PhotoId val photoId: String,
    private val api: Api,
    private val router: Router,
    private val job: Job
) : Presenter<PhotoContract.View>(v), PhotoContract.Presenter {

    private val executor = AppExecutor()

    private var photo: Photo? = null

    override fun start() {
        photo?.let {
            view?.let { v ->
                v.showPhoto(it.urls.full)
                v.updateTitle(it.description)
            }
        } ?: request()
    }

    private fun request() {
        launch(executor.ui, parent = job) {
            try {
                photo = api.photo(photoId).await()
                photo?.let {
                    view?.showPhoto(it.urls.full)
                }
            } catch (e: HttpException) {
                router.showSystemMessage(e.message)
            } finally {

            }
        }
    }
}