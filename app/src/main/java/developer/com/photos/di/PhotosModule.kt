package developer.com.photos.di

import developer.com.photos.presentation.photos.PhotosContract
import developer.com.photos.presentation.photos.PhotosFragment
import developer.com.photos.presentation.photos.PhotosPresenter

class PhotosModule(fragment: PhotosFragment) : AndroidModule(fragment) {
    init {
        bind(PhotosContract.View::class.java).toInstance(fragment)
        bind(PhotosContract.Presenter::class.java).to(PhotosPresenter::class.java)
            .singletonInScope()
    }
}