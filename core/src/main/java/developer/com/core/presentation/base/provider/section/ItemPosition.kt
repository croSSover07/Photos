package developer.com.core.presentation.base.provider.section

class ItemPosition(val section: Int, val position: Int) {

    companion object {
        const val POSITION_HEADER = -1
        const val POSITION_FOOTER = -2
    }

    val isItem: Boolean get() = position >= 0
    val isHeader: Boolean get() = position == POSITION_HEADER
    val isFooter: Boolean get() = position == POSITION_FOOTER

    override fun toString() = "ItemPosition(section=$section, ${when (position) {
        POSITION_HEADER -> "HEADER"
        POSITION_FOOTER -> "FOOTER"
        else -> "feedId=$position"
    }})"
}