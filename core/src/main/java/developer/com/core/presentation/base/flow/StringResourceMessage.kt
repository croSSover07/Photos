package developer.com.core.presentation.base.flow

import android.support.annotation.StringRes
import ru.terrakok.cicerone.commands.Command

class StringResourceMessage(
    @StringRes val resource: Int,
    val arguments: Array<out Any> = emptyArray()
) : Command