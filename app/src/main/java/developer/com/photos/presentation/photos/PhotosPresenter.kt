package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.Presenter
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.photos.Flow
import developer.com.photos.data.model.Photo
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PhotosPresenter @Inject constructor(
    v: PhotosContract.View,
    private val router: Router
) : Presenter<PhotosContract.View>(v), PhotosContract.Presenter {
    override var provider: GetableProvider<Photo>? = null

    override fun start() = Unit
    override fun refresh() = Unit

    override fun navigateTo(position: Int) {
        router.navigateTo(Flow.Main.Photo(provider?.get(position)?.id ?: return))
    }

    override fun attach(p: GetableProvider<Photo>) {
        provider = p
    }
}