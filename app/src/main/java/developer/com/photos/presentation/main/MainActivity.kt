package developer.com.photos.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import developer.com.core.presentation.base.BaseNavigatorActivity
import developer.com.core.presentation.base.HasToolbarLayout
import developer.com.core.presentation.base.flow.FlowNavigator
import developer.com.photos.Flow
import developer.com.photos.R
import developer.com.photos.di.MainModule
import developer.com.photos.presentation.photo.PhotoFragment
import developer.com.photos.presentation.photos.PhotosFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Screen
import toothpick.Scope
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.appBarLayout as abl
import kotlinx.android.synthetic.main.activity_main.toolbar as tb

class MainActivity : BaseNavigatorActivity(), MainContract.View, HasToolbarLayout {

    override val layoutResId = R.layout.activity_main
    override val fragmentContainerId = R.id.fragmentContainer
    override val swipeRefreshLayout: SwipeRefreshLayout? get() = refreshLayout

    override val appBarLayout: AppBarLayout get() = abl
    override val toolbar: Toolbar get() = tb

    @Inject lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        presenter.start()
    }

    override fun installModules(scope: Scope) {
        super.installModules(scope)
        scope.installModules(MainModule(this))
    }

    override fun setNavigationIcon(iconId: Int) = Unit

    override val navigator = object : FlowNavigator(
        this, supportFragmentManager, fragmentContainerId
    ) {
        override fun createActivityIntent(screen: Screen?): Intent? = null
        override fun createFragment(screen: Screen) = when (screen) {
            is Flow.Main.Photos -> PhotosFragment()
            is Flow.Main.Photo -> PhotoFragment.newInstance(screen.id)
            else -> error("Unknown screen: ${screen.screenKey}")
        }
    }
}