package developer.com.photos.presentation.main

import developer.com.core.presentation.presenter.Presenter
import developer.com.photos.Flow
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter @Inject constructor(
    v: MainContract.View,
    private val router: Router
) : Presenter<MainContract.View>(v), MainContract.Presenter {

    override fun start() {
        router.replaceScreen(Flow.Main.Photos)
    }
}