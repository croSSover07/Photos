package developer.com.core.presentation.base.pager

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import developer.com.core.extension.weak
import java.lang.ref.WeakReference

class PagerAdapter(
    context: Context,
    manager: FragmentManager,
    vararg fragments: PageFragment
) : FragmentStatePagerAdapter(manager) {

    private val contextRef: WeakReference<Context> = context.weak()

    private val pages: MutableList<PageFragment> = fragments.toMutableList()
    private val fragments: SparseArray<WeakReference<Fragment>> = SparseArray(pages.size)

    fun setPages(fragments: List<PageFragment>, notifyDataSetChanged: Boolean = true) {
        pages.clear()
        pages.addAll(fragments)
        if (notifyDataSetChanged) {
            notifyDataSetChanged()
        }
    }

    override fun getItem(position: Int): Fragment {
        val fragment = pages[position].creator.newInstance()
        fragments.put(position, fragment.weak())
        return fragment
    }

    override fun getItemPosition(`object`: Any) = FragmentStatePagerAdapter.POSITION_NONE

    fun getFragment(position: Int): Fragment? = fragments[position]?.get()

    override fun getCount() = pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        val page = pages[position]
        if (page.title != null) {
            return page.title
        }
        if (page.titleResId != null) {
            return contextRef.get()?.getString(page.titleResId)
        }
        return super.getPageTitle(position)
    }
}