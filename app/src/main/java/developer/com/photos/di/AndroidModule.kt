package developer.com.photos.di

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.experimental.Job
import toothpick.config.Module

@Suppress("LeakingThis")
abstract class AndroidModule(owner: LifecycleOwner) : Module() {
    init {
        bind(Job::class.java).toInstance(AndroidJob(owner.lifecycle))
    }

    private class AndroidJob(lifecycle: Lifecycle) : Job by Job(), LifecycleObserver {
        init {
            lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun release() = cancel()
    }
}