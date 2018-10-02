package developer.com.photos.util

interface Downloader {
    fun downloadImage(url: String, name: String)
}