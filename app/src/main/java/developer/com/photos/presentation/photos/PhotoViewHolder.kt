package developer.com.photos.presentation.photos

import android.view.ViewGroup
import developer.com.core.presentation.base.adapter.ViewHolder
import developer.com.photos.R
import java.lang.ref.WeakReference

class PhotoViewHolder(
    parent: ViewGroup,
    l: WeakReference<ViewHolder.OnClickListener>?
):ViewHolder<ViewHolder.OnClickListener>(parent, l, R.layout.view_item_photo, true)