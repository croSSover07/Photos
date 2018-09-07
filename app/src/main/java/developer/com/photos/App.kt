package developer.com.photos

import android.app.Application
import developer.com.photos.di.AppModule
import developer.com.photos.di.NetworkModule
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {
    companion object {
        lateinit var SCOPE: Scope
    }

    override fun onCreate() {
        super.onCreate()

        val configuration = if (!BuildConfig.DEBUG) {
//            FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
//            MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())
            Configuration.forProduction().disableReflection()

        } else Configuration.forDevelopment().preventMultipleRootScopes()

        Toothpick.setConfiguration(configuration)

        SCOPE = Toothpick.openScope(this).also {
            it.installModules(AppModule(this), NetworkModule())
        }
    }
}