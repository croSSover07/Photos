package developer.com.core.common

import developer.com.core.presentation.view.Messageable
import kotlinx.coroutines.experimental.CoroutineExceptionHandler

interface ExceptionHandler : CoroutineExceptionHandler {
    fun attach(view: Messageable)
    fun proceed(exception: Throwable)
}