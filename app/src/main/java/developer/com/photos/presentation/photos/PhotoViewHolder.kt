package developer.com.photos.presentation.photos

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.photos.R
import java.lang.ref.WeakReference

class PhotoViewHolder(
    parent: ViewGroup,
    l: WeakReference<ViewHolder.OnClickListener>?
) : ViewHolder<ViewHolder.OnClickListener>(parent, l, R.layout.view_item_photo, true) {
    val image: ImageView = itemView.findViewById(R.id.imageView)
    val title: TextView = itemView.findViewById(R.id.titleText)
}