package developer.com.core.presentation.base.flow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.widget.Toast
import developer.com.core.BuildConfig
import developer.com.core.presentation.BaseScreen
import developer.com.core.presentation.util.Constant
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

abstract class FlowNavigator(
    protected val activity: FragmentActivity,
    manager: FragmentManager,
    containerId: Int
) : FlowSupportFragmentNavigator(manager, containerId) {

    private object RequestCode {
        const val TARGET = 0x1202
    }

    val currentFragment: Fragment? get() = manager.findFragmentById(containerId)

    abstract fun createActivityIntent(context: Context, screen: BaseScreen): Intent?
    abstract fun createFragment(screen: BaseScreen): Fragment

    @Deprecated(Constant.EMPTY, ReplaceWith("createFragment(screen)"), DeprecationLevel.WARNING)
    final override fun createFragment(screenKey: String?, data: Any?): Fragment =
        createFragment(data as BaseScreen)

    override fun applyCommand(command: Command) {
        try {
            when (command) {
                is FlowForward -> forwardFlow(command)
                is StringResourceMessage -> showSystemMessage(
                    activity.getString(command.resource, *command.arguments)
                )
                else -> super.applyCommand(command)
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) e.printStackTrace()
//            else Crashlytics.logException(e)
        }
    }

    private fun forwardFlow(command: FlowForward) {
        val intent = createActivityIntent(activity, command.transitionData as BaseScreen)
        if (intent != null) {
            val options = createStartActivityOptions(command, intent)
            checkAndStartActivity(command.screenKey, intent, options)

        } else {
            val fragment = createFragment(command.transitionData as BaseScreen)
            val transaction = manager.beginTransaction()

            if (command.parent is Fragment) {
                fragment.setTargetFragment(command.parent, RequestCode.TARGET)
            }

            setupFragmentTransactionAnimation(
                command,
                manager.findFragmentById(containerId),
                fragment,
                transaction
            )

            transaction
                .replace(containerId, fragment)
                .addToBackStack(command.screenKey)
                .commit()

            localStackCopy.add(command.screenKey)
        }
    }

    protected open fun createStartActivityOptions(command: Command, intent: Intent): Bundle? = null

    override fun forward(command: Forward) {
        val intent = createActivityIntent(activity, command.transitionData as BaseScreen)
        if (intent != null) {
            val options = createStartActivityOptions(command, intent)
            checkAndStartActivity(command.screenKey, intent, options)

        } else super.forward(command)
    }

    override fun replace(command: Replace) {
        val intent = createActivityIntent(activity, command.transitionData as BaseScreen)
        if (intent != null) {
            val options = createStartActivityOptions(command, intent)
            checkAndStartActivity(command.screenKey, intent, options)
        } else super.replace(command)
    }

    private fun checkAndStartActivity(
        screenKey: String,
        intent: Intent,
        options: Bundle?
    ) = if (intent.resolveActivity(activity.packageManager) != null) {
        activity.startActivity(intent, options)

    } else unexistingActivity(screenKey, intent)

    protected open fun unexistingActivity(screenKey: String, activityIntent: Intent) = Unit

    override fun showSystemMessage(message: String) = Toast.makeText(
        activity, message, Toast.LENGTH_SHORT
    ).show()

    override fun exit() = activity.finish()
}