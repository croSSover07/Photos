package developer.com.photos.data.model

import com.google.gson.annotations.SerializedName

class Photo(
    override val id: String,
    @SerializedName("created_at")
    val createAt: String,
    val width: Int,
    val height: Int,
    val likes: Int,
    val description: String,
    val urls: Urls,
    val links: Links
) : WithIdentifier