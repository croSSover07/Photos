package developer.com.photos.util

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI

class AppExecutor : Executor {
    override val ui = UI
    override val io = DefaultDispatcher
}