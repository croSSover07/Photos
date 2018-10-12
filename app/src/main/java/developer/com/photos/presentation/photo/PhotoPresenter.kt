package developer.com.photos.presentation.photo

import developer.com.core.common.ExceptionHandler
import developer.com.core.presentation.presenter.ExceptionHandlingPresenter
import developer.com.core.util.Executor
import developer.com.photos.data.model.Photo
import developer.com.photos.data.net.Api
import developer.com.photos.di.qualifier.PhotoId
import developer.com.photos.util.Downloader
import developer.com.photos.util.WallPaperManager
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import ru.gildor.coroutines.retrofit.await
import javax.inject.Inject

class PhotoPresenter @Inject constructor(
    v: PhotoContract.View,
    @PhotoId val photoId: String,
    private val api: Api,
    job: Job,
    executor: Executor,
    handler: ExceptionHandler,
    private val wallpaperManager: WallPaperManager,
    private val downloader: Downloader
) : ExceptionHandlingPresenter<PhotoContract.View>(v, job, executor, handler),
    PhotoContract.Presenter {

    private var photo: Photo? = null

    override fun start() {
        photo?.let {
            view?.let { v ->
                v.showPhoto(it)
            }
        } ?: request()
    }

    override fun setWallpaper() {
        wallpaperManager.setWallpaper(photo?.urls?.full ?: return)
    }

    override fun download() {
        photo?.let {
            downloader.downloadImage(it.downloadLink, it.id)
        }
    }

    private fun request() {
        launch {
            photo = api.photo(photoId).await()
            photo?.let {
                view?.showPhoto(it)
            }
        }
    }
}