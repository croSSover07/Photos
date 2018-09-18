package developer.com.photos.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import developer.com.core.presentation.BaseScreen
import developer.com.core.presentation.base.BaseNavigatorActivity
import developer.com.core.presentation.base.flow.FlowNavigator
import developer.com.photos.R
import developer.com.photos.Screen
import developer.com.photos.di.MainModule
import developer.com.photos.presentation.photo.PhotoFragment
import developer.com.photos.presentation.photos.PhotosFragment
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.Scope
import javax.inject.Inject

class MainActivity : BaseNavigatorActivity(), MainContract.View {

    override val layoutResId = R.layout.activity_main
    override val fragmentContainerId = R.id.fragmentContainer
    override val swipeRefreshLayout: SwipeRefreshLayout? get() = refreshLayout

    @Inject lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.start()
    }

    override fun installModules(scope: Scope) {
        super.installModules(scope)
        scope.installModules(MainModule(this))
    }

    override val navigator = object : FlowNavigator(
        this, supportFragmentManager, fragmentContainerId
    ) {
        override fun createActivityIntent(context: Context, screen: BaseScreen): Intent? = null
        override fun createFragment(screen: BaseScreen) = when (screen) {
            is Screen.Main.Photos -> PhotosFragment()
            is Screen.Main.Photo -> PhotoFragment.newInstance(screen.id)
            else -> error("Unknown screen: ${screen.getKey()}")
        }
    }
}