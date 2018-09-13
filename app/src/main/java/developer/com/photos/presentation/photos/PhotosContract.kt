package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.Starting
import developer.com.core.presentation.base.Updatable
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.photos.data.model.Photo

interface PhotosContract {
    interface View : Updatable
    interface Presenter : Starting {
        val provider: GetableProvider<Photo>
    }
}