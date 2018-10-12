package developer.com.photos.presentation.photos

import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.core.presentation.presenter.Refreshing
import developer.com.core.presentation.presenter.Starting
import developer.com.core.presentation.view.Messageable
import developer.com.core.presentation.view.Refreshable
import developer.com.core.presentation.view.Updatable
import developer.com.photos.data.model.Photo

interface PhotosContract {
    interface View : Updatable, Refreshable, Messageable
    interface Presenter : Starting,
        Refreshing {
        var provider: GetableProvider<Photo>?
        fun navigateTo(position: Int)
        fun attach(p: GetableProvider<Photo>)
    }
}