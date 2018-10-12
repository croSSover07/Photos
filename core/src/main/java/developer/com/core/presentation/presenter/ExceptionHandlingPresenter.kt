package developer.com.core.presentation.presenter

import developer.com.core.common.ExceptionHandler
import developer.com.core.presentation.view.Attachable
import developer.com.core.presentation.view.Messageable
import developer.com.core.util.Executor
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

abstract class ExceptionHandlingPresenter<V>(
    view: V,
    job: Job,
    executor: Executor,
    protected val handler: ExceptionHandler
) : CoroutinePresenter<V>(view, job, executor) where V : Attachable, V : Messageable {
    override val coroutineContext: CoroutineContext get() = executor.ui + job + handler
}