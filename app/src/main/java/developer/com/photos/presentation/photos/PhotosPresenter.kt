package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.Presenter
import developer.com.core.presentation.base.flow.FlowRouter
import developer.com.core.presentation.base.provider.MutableListProvider
import developer.com.photos.data.model.Photo
import javax.inject.Inject

class PhotosPresenter @Inject constructor(
    v: PhotosContract.View,
    router: FlowRouter
) : Presenter<PhotosContract.View>(v), PhotosContract.Presenter {
    override val provider = MutableListProvider<Photo>()

    init {
//        only for test
//        provider.add(Photo())
//        provider.add(Photo())
//        provider.add(Photo())
    }
}