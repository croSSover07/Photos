package developer.com.photos.di

import developer.com.photos.di.qualifier.PhotoId
import developer.com.photos.presentation.photo.PhotoContract
import developer.com.photos.presentation.photo.PhotoFragment
import developer.com.photos.presentation.photo.PhotoPresenter
import developer.com.photos.util.AndroidDownloader
import developer.com.photos.util.AndroidWallpaperManager
import developer.com.photos.util.Downloader
import developer.com.photos.util.WallPaperManager

class PhotoModule(fragment: PhotoFragment) : AndroidModule(fragment) {
    init {
        bind(PhotoContract.View::class.java).toInstance(fragment)

        bind(String::class.java).withName(PhotoId::class.java).toInstance(fragment.photoId)
        bind(WallPaperManager::class.java).to(AndroidWallpaperManager::class.java).singletonInScope()
        bind(Downloader::class.java).to(AndroidDownloader::class.java).singletonInScope()

        bind(PhotoContract.Presenter::class.java).to(PhotoPresenter::class.java).singletonInScope()
    }
}