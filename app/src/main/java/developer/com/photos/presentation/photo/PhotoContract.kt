package developer.com.photos.presentation.photo

import developer.com.core.presentation.base.Attachable
import developer.com.core.presentation.base.Starting
import developer.com.core.presentation.base.Titleable

interface PhotoContract {
    interface View: Attachable, Titleable {
        fun showPhoto(url: String)
    }
    interface Presenter: Starting
}