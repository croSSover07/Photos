package developer.com.photos.presentation.photos

import developer.com.core.common.ExceptionHandler
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.core.presentation.presenter.ExceptionHandlingPresenter
import developer.com.core.util.Executor
import developer.com.photos.Flow
import developer.com.photos.data.model.Photo
import kotlinx.coroutines.experimental.Job
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PhotosPresenter @Inject constructor(
    v: PhotosContract.View,
    private val router: Router,
    job: Job,
    executor: Executor,
    handler: ExceptionHandler
) : ExceptionHandlingPresenter<PhotosContract.View>(v, job, executor, handler),
    PhotosContract.Presenter {
    override var provider: GetableProvider<Photo>? = null

    override fun refresh() = Unit

    override fun navigateTo(position: Int) {
        router.navigateTo(Flow.Main.Photo(provider?.get(position)?.id ?: return))
    }

    override fun attach(p: GetableProvider<Photo>) {
        provider = p
    }
}