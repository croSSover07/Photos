package developer.com.photos.presentation.photos

import android.arch.paging.DataSource
import developer.com.photos.data.model.Photo
import developer.com.photos.data.net.Api
import developer.com.photos.data.source.PagingDataSource
import retrofit2.Call
import javax.inject.Inject

class PhotosDataSource(private val api: Api) : PagingDataSource<Photo>() {

    class Factory @Inject constructor(
        private val api: Api
    ) : DataSource.Factory<Int, Photo>() {
        override fun create(): DataSource<Int, Photo> = PhotosDataSource(api)
    }

    override fun request(page: Int, perPage: Int): Call<List<Photo>> = api.photos(page, perPage)
}