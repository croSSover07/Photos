package developer.com.photos.data.model

import com.google.gson.annotations.SerializedName

class Photo(
    override val id: String,
    @SerializedName("created_at")
    val createAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val description: String,
    val urls: Urls
) : WithIdentifier