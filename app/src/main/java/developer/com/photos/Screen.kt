package developer.com.photos

import developer.com.core.presentation.BaseScreen

sealed class Screen : BaseScreen {

    override fun getKey(): String = this::class.java.simpleName

    interface Main {
        object Photos : Screen()
    }
}