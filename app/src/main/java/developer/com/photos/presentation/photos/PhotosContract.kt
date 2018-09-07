package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.Attachable
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.photos.data.model.Photo

interface PhotosContract {
    interface View: Attachable
    interface Presenter {
        val provider: GetableProvider<Photo>
    }
}