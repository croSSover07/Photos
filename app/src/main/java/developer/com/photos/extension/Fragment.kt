package developer.com.photos.extension

import android.support.v4.app.Fragment
import developer.com.photos.presentation.main.MainActivity

val Fragment.mainActivity: MainActivity? get() = activity as? MainActivity