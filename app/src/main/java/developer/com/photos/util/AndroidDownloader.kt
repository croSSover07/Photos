package developer.com.photos.util

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.net.Uri
import javax.inject.Inject

class AndroidDownloader @Inject constructor(
    context: Context
) : Downloader {

    companion object {
        private const val IMAGE_TYPE = "image/jpeg"
    }

    private val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun downloadImage(url: String, name: String) {
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setTitle(name)
            setMimeType(IMAGE_TYPE)
            setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            allowScanningByMediaScanner()
        }
        manager.enqueue(request)

    }
}