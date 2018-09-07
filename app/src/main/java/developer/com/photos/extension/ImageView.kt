package developer.com.photos.extension

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import developer.com.core.presentation.util.Constant
import developer.com.photos.App

private val ImageView.glide: RequestManager get() = App.SCOPE.getInstance(RequestManager::class.java)

fun <T> ImageView.load(
    model: T?, transformations: Array<Transformation<Bitmap>>? = null,
    strategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
    fallback: Int = Constant.NO_ID,
    colorize: Int = Constant.NO_ID,
    placeholder: Boolean = false,
    requestListener: RequestListener<T?, GlideDrawable>? = null
) {
    glide.load(model).also { r ->
        transformations?.let { r.bitmapTransform(*it) }
        if (fallback != Constant.NO_ID) {
            if (colorize != Constant.NO_ID) {
                val drawable = ContextCompat.getDrawable(context, fallback)!!.mutate()
                    .constantState.newDrawable()
                val color = ContextCompat.getColor(context, colorize)
                DrawableCompat.setTint(drawable, color)

                r.fallback(drawable).error(drawable)
                if (placeholder) r.placeholder(drawable)

            } else r.fallback(fallback).error(fallback).also {
                if (placeholder) it.placeholder(fallback)
            }
        }
    }.diskCacheStrategy(strategy).listener(requestListener).also { r ->
        r.into(this)
    }
}