package developer.com.photos.di

import android.arch.paging.DataSource
import developer.com.photos.presentation.photos.PhotosContract
import developer.com.photos.presentation.photos.PhotosDataSource
import developer.com.photos.presentation.photos.PhotosFragment
import developer.com.photos.presentation.photos.PhotosPresenter

class PhotosModule(fragment: PhotosFragment) : AndroidModule(fragment) {
    init {
        bind(PhotosContract.View::class.java).toInstance(fragment)
        bind(PhotosContract.Presenter::class.java).to(PhotosPresenter::class.java)
            .singletonInScope()

        bind(DataSource.Factory::class.java).to(PhotosDataSource.Factory::class.java)
            .singletonInScope()
    }
}