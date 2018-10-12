package developer.com.photos

import ru.terrakok.cicerone.Screen

sealed class Flow : Screen() {

    interface Main {
        object Photos : Screen()
        class Photo(val id: String) : Screen()
    }
}