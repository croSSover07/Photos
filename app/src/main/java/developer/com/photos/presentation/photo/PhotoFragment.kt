package developer.com.photos.presentation.photo

import android.Manifest
import android.os.Bundle
import android.view.View
import developer.com.core.extension.isPermissionGranted
import developer.com.core.extension.isVisible
import developer.com.core.extension.requiredArguments
import developer.com.core.presentation.base.BaseFragment
import developer.com.core.presentation.util.Constant
import developer.com.photos.R
import developer.com.photos.data.model.Photo
import developer.com.photos.di.PhotoModule
import developer.com.photos.extension.load
import developer.com.photos.extension.mainActivity
import kotlinx.android.synthetic.main.fragment_photo.*
import toothpick.Scope
import javax.inject.Inject

class PhotoFragment : BaseFragment(), PhotoContract.View, View.OnClickListener {
    companion object {
        private const val EXTRA = "EXTRA"

        private const val PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

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

    private val isPermissionGranted get() = context?.isPermissionGranted(PERMISSION) == true

    override fun installModules(scope: Scope) = scope.installModules(PhotoModule(this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()

        setWallView.setOnClickListener(this)
        downloadsView.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != Constant.RequestCode.WRITE_EXTERNAL) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (isPermissionGranted(PERMISSION, permissions, grantResults)) {
            presenter.setWallpaper()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.setWallView -> {
                if (!isPermissionGranted) {
                    requestPermissions(arrayOf(PERMISSION), Constant.RequestCode.WRITE_EXTERNAL)
                } else presenter.setWallpaper()
            }
            R.id.downloadsView -> presenter.download()
        }
    }

    override fun showPhoto(photo: Photo) {
        imageView.load(photo.urls?.full)

        photo.description.let {
            mainActivity?.supportActionBar?.title = it
            toolbarTitle = it
        }

        photo.instagramName?.let {
            userText.text = it
        } ?: apply {
            userText.isVisible = false
            userTitle.isVisible = false
        }

        widthText.text = "${photo.width}"
        heightText.text = "${photo.height}"

        photo.locationTitle?.let {
            locationText.text = it
        } ?: apply {
            locationText.isVisible = false
            locationTitle.isVisible = false
        }

        photo.views?.let {
            viewsText.text = it.toString()
        } ?: apply {
            viewsText.isVisible = false
            viewsTitle.isVisible = false
        }

        photo.downloads?.let {
            downloadsText.text = it.toString()
        } ?: apply {
            downloadsText.isVisible = false
            downloadsView.isVisible = false
        }

        likesText.text = "${photo.likes}"
    }
}