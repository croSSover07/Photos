package developer.com.photos.data.net

import developer.com.core.data.net.BaseApi
import developer.com.photos.data.model.Order
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api : BaseApi {
    @GET("/photos")
    fun photos(
        @Query(Key.PAGE) page: Int = 1,
        @Query(Key.PER_PAGE) perPage: Int = 10,
        @[Order Query(Key.ORDER_BY)] orderBy: String = Order.LATEST
    ): Call<Any>

    @GET("search/photos")
    fun search(
        @Query(Key.QUERY) query: String,
        @Query(Key.PAGE) page: Int = 1,
        @Query(Key.PER_PAGE) perPage: Int = 10
    ): Call<Any>
}