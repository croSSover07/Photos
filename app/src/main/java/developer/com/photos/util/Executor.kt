package developer.com.photos.util

import kotlinx.coroutines.experimental.CoroutineDispatcher

interface Executor {
    val io: CoroutineDispatcher
    val ui: CoroutineDispatcher
    val network: CoroutineDispatcher
}