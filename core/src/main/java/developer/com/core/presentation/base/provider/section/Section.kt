package developer.com.core.presentation.base.provider.section

class Section<out T>(
    val section: T? = null,
    var hasHeader: Boolean = false,
    var hasFooter: Boolean = false
)