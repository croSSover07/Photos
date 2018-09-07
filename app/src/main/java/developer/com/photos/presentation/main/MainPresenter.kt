package developer.com.photos.presentation.main

import developer.com.core.presentation.base.Presenter
import developer.com.core.presentation.base.flow.FlowRouter
import developer.com.photos.Screen
import javax.inject.Inject

class MainPresenter @Inject constructor(
    v: MainContract.View,
    private val router: FlowRouter
) : Presenter<MainContract.View>(v), MainContract.Presenter {

    override fun start() {
        router.replaceScreen(Screen.Main.Photos)
    }
}