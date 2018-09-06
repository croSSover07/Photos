package developer.com.core.extension

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import developer.com.core.presentation.base.provider.GetableProvider
import java.lang.ref.WeakReference

fun <T> T.weak() = WeakReference(this)
val Any.className: String get() = this.javaClass.simpleName
var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) = changeVisibility(value)

fun View.changeVisibility(visible: Boolean, invisibleState: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisibleState
}

val <T> GetableProvider<T>.lastIndex: Int get() = itemCount - 1

val Fragment.requiredArguments: Bundle get() = requireNotNull(arguments)

fun Context.notImplemented() {
    Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
}

fun isPermissionGranted(
    permission: String,
    permissions: Array<out String>,
    grantResults: IntArray
) = grantResults.elementAtOrNull(
    permissions.indexOf(permission)
)?.let { it == PackageManager.PERMISSION_GRANTED } == true

fun Context.isPermissionGranted(permission: String) = ActivityCompat.checkSelfPermission(
    this, permission
) == PackageManager.PERMISSION_GRANTED

fun Context.isPermissionsGranted(vararg permissions: String) = permissions.all {
    isPermissionGranted(it)
}

fun Boolean.toInt() = if (this) 1 else 0
