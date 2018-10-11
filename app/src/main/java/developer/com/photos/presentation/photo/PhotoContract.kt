package developer.com.photos.presentation.photo

import developer.com.core.presentation.base.Attachable
import developer.com.core.presentation.base.Refreshable
import developer.com.core.presentation.base.Starting
import developer.com.photos.data.model.Photo

interface PhotoContract {
    interface View : Attachable, Refreshable {
        fun showPhoto(photo: Photo)
        fun showInstaProfile(name: String)
    }

    interface Presenter : Starting {
        fun setWallpaper()
        fun download()
    }
}