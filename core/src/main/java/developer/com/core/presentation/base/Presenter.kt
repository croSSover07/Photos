package developer.com.core.presentation.base

import developer.com.core.extension.weak

abstract class Presenter<out V : Attachable>(view: V) {
    private val reference = view.weak()
    protected val view: V?
        get() = reference.get()?.let {
            if (it.isAttached) it else null
        }
}