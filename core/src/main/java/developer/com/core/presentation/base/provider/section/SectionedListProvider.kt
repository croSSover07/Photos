package developer.com.core.presentation.base.provider.section

open class SectionedListProvider<S, I>(
    val sections: MutableList<SectionItems<S, I>> = mutableListOf()
) : SectionedProvider<S, I> {

    override fun getSectionsCount() = sections.size
    override fun getItemsCountInSection(section: Int) =
        sections.elementAtOrNull(section)?.items?.size ?: 0

    override fun hasHeaderForSection(section: Int) =
        sections.elementAtOrNull(section)?.section?.hasHeader == true

    override fun hasFooterForSection(section: Int) =
        sections.elementAtOrNull(section)?.section?.hasFooter == true

    override fun getItemPosition(absolutePosition: Int): ItemPosition {
        var currentPosition = absolutePosition
        for ((index, sectionItems) in sections.withIndex()) {
            if (currentPosition >= sectionItems.rawCount) {
                currentPosition -= sectionItems.rawCount
            } else {
                return if (currentPosition == 0 && sectionItems.section.hasHeader) {
                    ItemPosition(index, ItemPosition.POSITION_HEADER)
                } else {
                    if (sectionItems.section.hasHeader) currentPosition--
                    when (currentPosition) {
                        in 0 until sectionItems.items.size -> ItemPosition(index, currentPosition)
                        else -> ItemPosition(index, ItemPosition.POSITION_FOOTER)
                    }
                }
            }
        }

        error("Absolute feedId: $absolutePosition is out of bounds.")
    }

    override fun getItemForPosition(position: ItemPosition) =
        getSectionItems(position.section)?.items?.elementAtOrNull(position.position)

    override fun getSection(section: Int) = getSectionItems(section)?.section
    override fun getSectionItems(section: Int) = sections.elementAtOrNull(section)

    override fun setSection(index: Int, section: Section<S>) {
        val sectionItems = sections.elementAtOrNull(index)
        if (sectionItems != null) {
            sectionItems.section = section
        } else sections.add(index, SectionItems(section))
    }

    override fun insertSection(index: Int, section: Section<S>) =
        sections.add(index, SectionItems(section, mutableListOf()))

    override fun add(section: Section<S>, items: MutableList<I>) {
        sections.add(SectionItems(section, items))
    }

    override fun setSectionItems(section: Int, items: List<I>) {
        val sectionItems = sections.elementAtOrNull(section)
        if (sectionItems != null) {
            sectionItems.items.clear()
            sectionItems.items.addAll(items)

        } else sections.add(section, SectionItems(items = items.toMutableList()))
    }
}

private val SectionItems<*, *>.rawCount: Int
    get() {
        var count = items.size
        if (section.hasHeader) count++
        if (section.hasFooter) count++
        return count
    }