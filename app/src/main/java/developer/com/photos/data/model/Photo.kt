package developer.com.photos.data.model

class Photo(
    override val id: String,
    val createAt: String?,
    val width: Int,
    val height: Int,
    val likes: Int,
    val description: String?,
    val urls: Urls?,
    val downloadLink: String,
    val locationTitle: String?,
    val instagramName: String?,
    val views: Int?,
    val downloads: Int?
) : WithIdentifier