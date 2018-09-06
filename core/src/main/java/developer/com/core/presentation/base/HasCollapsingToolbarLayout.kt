package developer.com.core.presentation.base

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar

interface HasCollapsingToolbarLayout {
    val collapsingToolbarLayout: CollapsingToolbarLayout
    val appBarLayout: AppBarLayout
    val toolbar: Toolbar

    fun setNavigationIcon(iconId: Int)
}