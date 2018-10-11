package developer.com.photos.presentation.photos

import android.arch.paging.DataSource
import developer.com.photos.data.model.Photo
import developer.com.photos.data.model.ResponsePhotos
import developer.com.photos.data.net.Api
import developer.com.photos.data.source.PagingDataSource
import retrofit2.Call
import ru.gildor.coroutines.retrofit.await
import javax.inject.Inject

class PhotosDataSource(
    private val api: Api,
    private var query: String?
) : PagingDataSource<Photo>() {

    private val isSearchMode: Boolean get() = query != null

    class Factory @Inject constructor(private val api: Api) : DataSource.Factory<Int, Photo>() {
        var query: String? = null

        override fun create(): DataSource<Int, Photo> = PhotosDataSource(api, query)

        fun search(q: String?) {
            query = q
        }
    }

    private fun request(page: Int, perPage: Int): Call<*> =
        if (isSearchMode) api.search(query, page, perPage) else api.photos(page, perPage)

    override suspend fun process(page: Int, perPage: Int): List<Photo> = if (isSearchMode) {
        (request(page, perPage).await() as ResponsePhotos).results
    } else {
        (request(page, perPage).await() as List<Photo>)
    }
}