package developer.com.core.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import developer.com.core.R
import developer.com.core.presentation.base.pager.PagerAdapter

abstract class BaseTabFragment : BaseFragment() {

    override val layoutResId = R.layout.fragment_pager

    open val viewPagerId = R.id.viewPager

    protected lateinit var adapter: PagerAdapter
    protected lateinit var viewPager: ViewPager

    var tabLayout: TabLayout? = null

    abstract fun createAdapter(): PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = createAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? HasCollapsingToolbarLayout)?.appBarLayout?.addView(
            TabLayout(context).apply {
                val whiteColor = ContextCompat.getColor(context, android.R.color.white)
                setTabTextColors(ContextCompat.getColor(context, R.color.white_70pct), whiteColor)
                setSelectedTabIndicatorColor(whiteColor)
                tabLayout = this
            }
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(viewPagerId)
        viewPager.adapter = adapter
        onTabLayoutCreated(tabLayout ?: return)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyTabLayout(tabLayout ?: return)
    }

    @CallSuper
    open fun onTabLayoutCreated(tabLayout: TabLayout) {
        tabLayout.setupWithViewPager(viewPager)
    }

    open fun onDestroyTabLayout(tabLayout: TabLayout) {
        (activity as? HasCollapsingToolbarLayout)?.appBarLayout?.removeView(tabLayout)
    }
}