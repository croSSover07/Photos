package developer.com.core.presentation.base.pager

import android.support.v4.app.Fragment
import developer.com.core.presentation.DummyFragment

class PageFragment(
    val creator: Creator,
    val titleResId: Int? = null,
    val title: CharSequence? = null
) {
    interface Creator {
        fun newInstance(): Fragment

        companion object {
            val EMPTY_FRAGMENT: Creator
                get() = object : Creator {
                    override fun newInstance() = Fragment()
                }
            val NOT_READY: Creator
                get() = object : Creator {
                    override fun newInstance() = DummyFragment.newInstance("Not ready yet")
                }
        }
    }
}