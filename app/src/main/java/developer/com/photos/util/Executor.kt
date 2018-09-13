package developer.com.photos.util

import kotlin.coroutines.experimental.CoroutineContext

interface Executor {
    val io: CoroutineContext
    val ui: CoroutineContext
//    val net: CoroutineContext
}