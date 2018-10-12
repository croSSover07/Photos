package developer.com.core.common

import developer.com.core.extension.unit
import developer.com.core.extension.weak
import developer.com.core.presentation.view.Messageable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import java.lang.ref.WeakReference
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.CoroutineContext

open class DefaultExceptionHandler : AbstractCoroutineContextElement(CoroutineExceptionHandler),
    ExceptionHandler {

    protected var reference: WeakReference<Messageable>? = null
    protected val view: Messageable? get() = reference?.get()

    override fun proceed(exception: Throwable) = view?.show(exception.localizedMessage).unit()

    override fun attach(view: Messageable) {
        reference = view.weak()
    }

    final override val key: CoroutineContext.Key<*> get() = CoroutineExceptionHandler.Key
    final override fun handleException(context: CoroutineContext, exception: Throwable) =
        proceed(exception)
}