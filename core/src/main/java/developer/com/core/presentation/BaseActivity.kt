package developer.com.core.presentation

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import developer.com.core.presentation.base.Attachable
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieSupportActivityModule

abstract class BaseActivity : AppCompatActivity(), Attachable {

    protected var scope: Scope? = null

    abstract val layoutResId: Int
    protected open val fragmentContainerId = 0

    override var isAttached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        scope = createScope().also { installModules(it) }
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)

        isAttached = true
        if (layoutResId != 0) {
            setContentView(layoutResId)
        }
    }

    override fun onDestroy() {
        isAttached = false
        super.onDestroy()
        Toothpick.closeScope(this)
        scope = null
    }

    protected open fun createScope(): Scope = Toothpick.openScopes(application, this)

    @CallSuper
    protected open fun installModules(scope: Scope) {
        scope.installModules(SmoothieSupportActivityModule(this))
    }
}