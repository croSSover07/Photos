package developer.com.photos.presentation.main

import developer.com.core.presentation.base.Attachable
import developer.com.core.presentation.base.Starting

interface MainContract {
    interface View: Attachable
    interface Presenter: Starting
}