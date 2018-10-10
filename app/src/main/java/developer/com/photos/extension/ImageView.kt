package developer.com.photos.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import developer.com.photos.App

val ImageView.glide: RequestManager get() = App.SCOPE.getInstance(RequestManager::class.java)

fun <T> ImageView.load(
    model: T?,
    strategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
    requestListener: RequestListener<Drawable?>? = null
) = glide.load(model)
    .apply(RequestOptions.diskCacheStrategyOf(strategy))
    .transition(DrawableTransitionOptions.withCrossFade())
    .listener(requestListener)
    .into(this)