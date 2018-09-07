package developer.com.core.presentation.base.flow

import android.support.annotation.StringRes
import developer.com.core.presentation.BaseScreen
import developer.com.core.presentation.base.Attachable
import developer.com.core.presentation.util.Constant
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Forward

class FlowRouter : Router() {

    companion object {
        private const val errorMessage = "Use methods with class Screen instead screenKey"
    }

    fun navigateTo(screen: BaseScreen, parent: Attachable?) = executeCommands(
        if (parent != null) {
            FlowForward(screen, parent)
        } else {
            Forward(screen.getKey(), screen)
        }
    )

    fun newScreenChain(screen: BaseScreen) = super.newScreenChain(screen.getKey(), screen)
    fun navigateTo(screen: BaseScreen) = super.navigateTo(screen.getKey(), screen)
    fun backTo(screen: BaseScreen?) = super.backTo(screen?.getKey())
    fun replaceScreen(screen: BaseScreen) = super.replaceScreen(screen.getKey(), screen)
    fun newRootScreen(screen: BaseScreen) = super.newRootScreen(screen.getKey(), screen)

    fun exitWithResultAndSkip(resultCode: Int, result: Any?) {
        exitWithResult(resultCode, result)
        sendResult(Constant.ResultCode.SKIP_SCREEN, null)
    }

    fun exitAndSkip() {
        exit()
        sendResult(Constant.ResultCode.SKIP_SCREEN, null)
    }

    fun showSystemMessage(@StringRes resource: Int, vararg args: Any) {
        executeCommands(StringResourceMessage(resource, args))
    }

    //region Deprecated
    @Deprecated(errorMessage, ReplaceWith("newScreenChain(screen)"), DeprecationLevel.ERROR)
    override fun newScreenChain(screenKey: String?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("newScreenChain(screen)"), DeprecationLevel.ERROR)
    override fun newScreenChain(screenKey: String?, data: Any?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("navigateTo(screen)"), DeprecationLevel.ERROR)
    override fun navigateTo(screenKey: String?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("navigateTo(screen)"), DeprecationLevel.ERROR)
    override fun navigateTo(screenKey: String?, data: Any?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("backTo(screen)"), DeprecationLevel.ERROR)
    override fun backTo(screenKey: String?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("replaceScreen(screen)"), DeprecationLevel.ERROR)
    override fun replaceScreen(screenKey: String?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("replaceScreen(screen)"), DeprecationLevel.ERROR)
    override fun replaceScreen(screenKey: String?, data: Any?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("newRootScreen(screen)"), DeprecationLevel.ERROR)
    override fun newRootScreen(screenKey: String?): Unit = error(errorMessage)

    @Deprecated(errorMessage, ReplaceWith("newRootScreen(screen)"), DeprecationLevel.ERROR)
    override fun newRootScreen(screenKey: String?, data: Any?): Unit = error(errorMessage)
    //endregion
}