package developer.com.core.presentation.presenter

import developer.com.core.extension.weak
import developer.com.core.presentation.view.Attachable

abstract class Presenter<out V : Attachable>(view: V) {
    private val reference = view.weak()
    protected val view: V?
        get() = reference.get()?.let {
            if (it.isAttached) it else null
        }
}