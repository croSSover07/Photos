package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.Refreshable
import developer.com.core.presentation.base.Refreshing
import developer.com.core.presentation.base.Starting
import developer.com.core.presentation.base.Updatable
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.photos.data.model.Photo

interface PhotosContract {
    interface View : Updatable, Refreshable
    interface Presenter : Starting, Refreshing {
        var provider: GetableProvider<Photo>?
        fun navigateTo(position: Int)
        fun attach(p: GetableProvider<Photo>)
    }
}