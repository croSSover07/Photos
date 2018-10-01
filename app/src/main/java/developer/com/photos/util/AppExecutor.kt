package developer.com.photos.util

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newSingleThreadContext

class AppExecutor : Executor {
    override val ui = UI
    override val io = DefaultDispatcher
    override val network = newSingleThreadContext("network")
}