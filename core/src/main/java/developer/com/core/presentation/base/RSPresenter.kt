package developer.com.core.presentation.base

import developer.com.core.data.source.SubscribableDataSource
import developer.com.core.data.source.Repository

abstract class RSPresenter<out VIEW>(
    v: VIEW
) : Presenter<VIEW>(v),
    Starting,
    HasOptionalRepository,
    SubscribableDataSource.Callback where
VIEW : Attachable, VIEW : Updatable, VIEW : Refreshable {

    override val repository: Repository<*, *>? get() = null

    override fun start() {
        val repository = repository ?: return

        view?.isRefreshing = repository.isEmpty
        repository.load()
    }

    override fun onDataLoaded() {
        view?.apply {
            isRefreshing = false
            update()
        }
    }

    override fun onDataNotAvailable(error: String?) {
        view?.isRefreshing = false
    }
}