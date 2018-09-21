package developer.com.photos.util

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import java.net.URL
import javax.inject.Inject

class AndroidWallpaperManager @Inject constructor(
    private val context: Context,
    private val job: Job
) : WallPaperManager {

    private val manager = WallpaperManager.getInstance(context)

    private val executor = AppExecutor()

    override fun setWallpaper(url: String) {
        launch(executor.io, parent = job) {
            set(url)
        }
    }

    private suspend fun set(url: String) {
        val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
        val uriImage = MediaStore.Images.Media.insertImage(
            context.contentResolver, bitmap, Uri.parse(url).lastPathSegment, null
        ).let { Uri.parse(it) }

        val intent = manager.getCropAndSetWallpaperIntent(uriImage).setFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}