package developer.com.core.presentation.base.provider.section

open class SectionItems<S, I>(
    var section: Section<S> = Section(),
    val items: MutableList<I> = mutableListOf()
)

class CuttedSectionItems<S, I>(
    section: Section<S>,
    val sourceList: MutableList<I>,
    from: Int = 0,
    to: Int = 0
) : SectionItems<S, I>(section, sourceList.subList(from, to))

class DummySectionItems<S, I>(
    section: Section<S>,
    val function: () -> Int
) : SectionItems<S, I>(section, mutableListOf())