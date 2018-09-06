package developer.com.core.presentation.base.provider.section

import developer.com.core.presentation.base.provider.Provider

interface SectionedProvider<S, I> : Provider {
    fun getSectionsCount(): Int
    fun getItemsCountInSection(section: Int = 0): Int

    fun hasHeaderForSection(section: Int = 0): Boolean
    fun hasFooterForSection(section: Int = 0): Boolean

    fun getItemPosition(absolutePosition: Int): ItemPosition

    fun getSection(section: Int = 0): Section<S>?
    fun setSection(index: Int = 0, section: Section<S>)
    fun insertSection(index: Int = 0, section: Section<S>)

    fun getSectionItems(section: Int = 0): SectionItems<S, I>?
    fun setSectionItems(section: Int = 0, items: List<I>)

    fun add(section: Section<S>, items: MutableList<I>)

    fun getItemForPosition(position: ItemPosition): I?

    override val itemCount: Int
        get() {
            var count = 0
            for (section in 0 until getSectionsCount()) {
                count += getItemsCountInSection(section)

                if (hasHeaderForSection(section)) count++
                if (hasFooterForSection(section)) count++
            }
            return count
        }
}