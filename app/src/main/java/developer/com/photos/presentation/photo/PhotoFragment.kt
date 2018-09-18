package developer.com.photos.presentation.photo

import android.os.Bundle
import android.view.View
import developer.com.core.extension.requiredArguments
import developer.com.core.presentation.base.BaseFragment
import developer.com.core.presentation.util.Constant
import developer.com.photos.R
import developer.com.photos.di.PhotoModule
import developer.com.photos.extension.load
import developer.com.photos.extension.mainActivity
import kotlinx.android.synthetic.main.fragment_photo.*
import toothpick.Scope
import javax.inject.Inject

class PhotoFragment : BaseFragment(), PhotoContract.View {
    companion object {
        private const val EXTRA = "EXTRA"
        fun newInstance(id: String) = PhotoFragment().apply {
            arguments = Bundle(1).also {
                it.putString(EXTRA, id)
            }
        }
    }

    override val layoutResId = R.layout.fragment_photo
    val photoId: String get() = requiredArguments.getString(EXTRA) ?: Constant.EMPTY

    override var toolbarTitle: CharSequence? = null
    @Inject lateinit var presenter: PhotoContract.Presenter

    override fun installModules(scope: Scope) = scope.installModules(PhotoModule(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun showPhoto(url: String) {
        imageView.load(url)
    }

    override fun updateTitle(title: String) {
        mainActivity?.supportActionBar?.title = title
        toolbarTitle = title
    }
}