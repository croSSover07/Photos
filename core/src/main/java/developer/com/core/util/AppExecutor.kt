package developer.com.core.util

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.newSingleThreadContext

class AppExecutor : Executor {
    override val io = Dispatchers.IO
    override val ui = Dispatchers.Main
    override val network = newSingleThreadContext("network")
}