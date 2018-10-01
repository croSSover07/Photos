package developer.com.photos.presentation.photos

import developer.com.core.presentation.util.Constant
import developer.com.photos.data.model.Photo
import developer.com.photos.data.net.Api
import developer.com.photos.data.source.PagingDataSource
import javax.inject.Inject

class PhotosDataSource @Inject constructor(
    private val api: Api
) : PagingDataSource<Photo>() {
    override fun request() = api.photos(page, Constant.ITEM_PER_PAGE)
}