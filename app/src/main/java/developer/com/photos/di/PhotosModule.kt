package developer.com.photos.di

import developer.com.photos.presentation.photos.PhotosContract
import developer.com.photos.presentation.photos.PhotosFragment
import developer.com.photos.presentation.photos.PhotosPresenter
import toothpick.config.Module

class PhotosModule(fragment: PhotosFragment) : Module() {
    init {
        bind(PhotosContract.View::class.java).toInstance(fragment)
        bind(PhotosContract.Presenter::class.java).to(PhotosPresenter::class.java)
            .singletonInScope()
    }
}