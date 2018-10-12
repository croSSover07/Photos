package developer.com.photos.presentation.photo

import developer.com.core.presentation.presenter.Starting
import developer.com.core.presentation.view.Attachable
import developer.com.core.presentation.view.Messageable
import developer.com.core.presentation.view.Refreshable
import developer.com.photos.data.model.Photo

interface PhotoContract {
    interface View : Attachable, Refreshable, Messageable {
        fun showPhoto(photo: Photo)
        fun showInstaProfile(name: String)
    }

    interface Presenter : Starting {
        fun setWallpaper()
        fun download()
    }
}