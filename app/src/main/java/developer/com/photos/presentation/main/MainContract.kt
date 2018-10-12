package developer.com.photos.presentation.main

import developer.com.core.presentation.presenter.Starting
import developer.com.core.presentation.view.Attachable

interface MainContract {
    interface View : Attachable
    interface Presenter : Starting
}