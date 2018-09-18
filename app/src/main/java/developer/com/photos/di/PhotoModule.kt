package developer.com.photos.di

import developer.com.photos.di.qualifier.PhotoId
import developer.com.photos.presentation.photo.PhotoContract
import developer.com.photos.presentation.photo.PhotoFragment
import developer.com.photos.presentation.photo.PhotoPresenter

class PhotoModule(fragment: PhotoFragment) : AndroidModule(fragment) {
    init {
        bind(PhotoContract.View::class.java).toInstance(fragment)
        bind(String::class.java).withName(PhotoId::class.java).toInstance(fragment.photoId)
        bind(PhotoContract.Presenter::class.java).to(PhotoPresenter::class.java).singletonInScope()
    }
}