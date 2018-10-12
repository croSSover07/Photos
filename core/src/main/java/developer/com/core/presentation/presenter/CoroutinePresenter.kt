package developer.com.core.presentation.presenter

import developer.com.core.presentation.view.Attachable
import developer.com.core.util.Executor
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

abstract class CoroutinePresenter<V : Attachable>(
    view: V,
    protected val job: Job,
    protected val executor: Executor
) : Presenter<V>(view), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = executor.ui + job
}