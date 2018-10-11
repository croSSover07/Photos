package developer.com.photos.data.model

import com.google.gson.annotations.SerializedName

class ResponsePhotos(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<Photo>
)