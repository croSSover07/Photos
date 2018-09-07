package developer.com.core.presentation.base.flow

import developer.com.core.presentation.BaseScreen
import developer.com.core.presentation.base.Attachable
import ru.terrakok.cicerone.commands.Forward

class FlowForward(
    screen: BaseScreen,
    val parent: Attachable?
) : Forward(screen.getKey(), screen)