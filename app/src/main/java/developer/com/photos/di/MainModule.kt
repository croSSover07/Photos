package developer.com.photos.di

import developer.com.photos.presentation.main.MainActivity
import developer.com.photos.presentation.main.MainContract
import developer.com.photos.presentation.main.MainPresenter
import toothpick.config.Module

class MainModule(activity: MainActivity) : Module() {
    init {
        bind(MainContract.View::class.java).toInstance(activity)
        bind(MainContract.Presenter::class.java).to(MainPresenter::class.java).singletonInScope()
    }
}