package developer.com.core.presentation.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.MenuItem
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Back
import javax.inject.Inject

abstract class BaseNavigatorActivity : BaseActivity() {

    protected abstract val navigator: Navigator

    open val swipeRefreshLayout: SwipeRefreshLayout? = null

    @Inject lateinit var holder: NavigatorHolder

    protected val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(
            fragmentContainerId
        ) as? BaseFragment

    var isRefreshing: Boolean
        get() = swipeRefreshLayout?.isRefreshing == true
        set(value) {
            swipeRefreshLayout?.isRefreshing = value
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            true

        } else super.onOptionsItemSelected(item)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swipeRefreshLayout?.let(this::configure)
    }

    @SuppressLint("RestrictedApi")
    override fun onPostResume() {
        super.onPostResume()
        supportActionBar?.setShowHideAnimationEnabled(false)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        holder.setNavigator(navigator)
    }

    override fun onPause() {
        holder.removeNavigator()
        super.onPause()
    }

    protected open fun configure(layout: SwipeRefreshLayout) {
        if (this is SwipeRefreshLayout.OnRefreshListener) {
            layout.setOnRefreshListener(this)
            layout.isEnabled = true
        } else {
            layout.setOnRefreshListener(null)
            layout.isEnabled = false
        }
    }

    override fun onBackPressed() {
        if (currentFragment?.onBackPressed() == false) {
            navigator.applyCommands(arrayOf(Back()))
        }
    }
}