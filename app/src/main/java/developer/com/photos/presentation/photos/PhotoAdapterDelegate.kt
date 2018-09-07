package developer.com.photos.presentation.photos

import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.core.presentation.base.adapter.delegate.TypedProviderDelegate
import developer.com.core.presentation.base.provider.GetableProvider
import developer.com.photos.data.model.Photo
import developer.com.photos.extension.load

class PhotoAdapterDelegate(
    provider: GetableProvider<Photo>,
    listener: ViewHolder.OnClickListener
) : TypedProviderDelegate<Photo, ViewHolder.OnClickListener, PhotoViewHolder>(
    provider, listener
) {
    override fun onCreateViewHolder(parent: ViewGroup) = PhotoViewHolder(parent, listener)

    override fun onBindViewHolder(
        position: Int,
        holder: PhotoViewHolder,
        provider: GetableProvider<Photo>,
        item: Photo
    ) {
        // TODO()
        holder.image.load("")
        holder.title.text = ""
    }
}
