package developer.com.photos.di

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import developer.com.core.common.DefaultExceptionHandler
import developer.com.core.common.ExceptionHandler
import developer.com.core.util.AppExecutor
import developer.com.core.util.Executor
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(app: Application) : Module() {
    init {
        bind(Context::class.java).toInstance(app)
        bind(RequestManager::class.java).toInstance(Glide.with(app))

        bind(Executor::class.java).toInstance(AppExecutor())
        bind(ExceptionHandler::class.java).toInstance(DefaultExceptionHandler())

        val cicerone = Cicerone.create(Router())
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}