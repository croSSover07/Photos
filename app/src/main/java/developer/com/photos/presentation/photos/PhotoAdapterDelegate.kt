package developer.com.photos.presentation.photos

import android.view.View
import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.core.presentation.base.adapter.delegate.ProviderDelegate
import developer.com.photos.data.model.Photo
import developer.com.photos.extension.load

class PhotoAdapterDelegate(
    listener: ViewHolder.OnClickListener
) : ProviderDelegate<Photo, ViewHolder.OnClickListener, PhotoViewHolder>(
    listener
) {
    override fun onCreateViewHolder(parent: ViewGroup) = PhotoViewHolder(parent, listener)

    override fun onBindViewHolder(position: Int, holder: PhotoViewHolder, item: Photo) {
        holder.image.load(item.urls.small)
        if (item.description.isNullOrEmpty()) {
            holder.title.visibility = View.GONE
        } else {
            holder.title.text = item.description
            holder.title.visibility = View.VISIBLE
        }
    }
}