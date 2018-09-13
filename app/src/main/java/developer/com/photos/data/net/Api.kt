package developer.com.photos.data.net

import developer.com.photos.data.model.Order
import developer.com.photos.data.model.Photo
import developer.com.photos.data.model.ResponsePhotos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/photos")
    fun photos(
        @Query(Key.PAGE) page: Int = 1,
        @Query(Key.PER_PAGE) perPage: Int = 10,
        @[Order Query(Key.ORDER_BY)] orderBy: String = Order.LATEST
    ): Call<Array<Photo>>

    @GET("search/photos")
    fun search(
        @Query(Key.QUERY) query: String,
        @Query(Key.PAGE) page: Int = 1,
        @Query(Key.PER_PAGE) perPage: Int = 10
    ): Call<ResponsePhotos>
}