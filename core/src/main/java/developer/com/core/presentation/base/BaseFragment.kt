package developer.com.core.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import developer.com.core.presentation.view.Messageable
import developer.com.core.presentation.view.OnBackPressedListener
import developer.com.core.presentation.view.Refreshable
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseFragment : Fragment(), Refreshable, Messageable,
    OnBackPressedListener {

    abstract val layoutResId: Int

    protected var scope: Scope? = null

    protected open val toolbarTitleRes = 0
    protected open val toolbarIndicatorRes = 0
    protected open val toolbarTitle: CharSequence?
        get() = toolbarTitleRes.let {
            if (it != 0) getText(it) else null
        }
    protected open val optionsMenuRes = 0

    protected val refreshLayout: SwipeRefreshLayout?
        get() = (activity as? BaseNavigatorActivity)?.swipeRefreshLayout

    override var isAttached = false
    override var isRefreshing: Boolean
        get() = refreshLayout?.isRefreshing == true
        set(value) {
            refreshLayout?.isRefreshing = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutResId, container, false)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (optionsMenuRes != 0) inflater.inflate(optionsMenuRes, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        scope = createScope().also { installModules(it) }
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        setHasOptionsMenu(optionsMenuRes != 0 || toolbarIndicatorRes != 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAttached = true
        refreshLayout?.let { configure(it) }
    }

    override fun onStart() {
        super.onStart()
        ((activity as? AppCompatActivity)?.supportActionBar)?.let { onActionBarReady(it) }
    }

    override fun onDestroyView() {
        isAttached = false
        super.onDestroyView()
    }

    override fun onDestroy() {
        Toothpick.closeScope(this)
        scope = null
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == android.R.id.home) {
        activity?.onBackPressed()
        true

    } else super.onOptionsItemSelected(item)

    protected open fun createScope(): Scope = Toothpick.openScopes(
        targetFragment ?: parentFragment ?: activity, this
    )

    protected open fun installModules(scope: Scope) = Unit

    protected open fun configure(layout: SwipeRefreshLayout) {
        if (this is SwipeRefreshLayout.OnRefreshListener && targetFragment == null) {
            layout.setOnRefreshListener(this)
            layout.isEnabled = true
        } else {
            layout.setOnRefreshListener(null)
            layout.isEnabled = false
        }
    }

    @CallSuper
    protected open fun onActionBarReady(bar: ActionBar) {
        if (parentFragment != null) return

        bar.title = toolbarTitle
    }

    override fun onBackPressed() = false

    override fun show(message: String, withDuration: Int, param: Int) {
        Toast.makeText(context ?: return, message, withDuration).show()
    }
}