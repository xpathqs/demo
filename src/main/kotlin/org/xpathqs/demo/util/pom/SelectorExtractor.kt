package org.xpathqs.demo.util.pom

import org.xpathqs.core.selector.base.*
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.block.allInnerSelectors
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.annotations.UI.Visibility.Companion.UNDEF_STATE
import kotlin.reflect.KVisibility
import kotlin.reflect.jvm.kotlinProperty

interface ISelectorExtractor {
    val staticSelectors : Collection<BaseSelector>
    val dynamicSelectors : Collection<BaseSelector>
}

class SelectorExtractor(
    val source: Block,
    val state: Int = UNDEF_STATE,
    private val stateFilter: IStateFilter = StateFilter()
) : ISelectorExtractor {
    override val staticSelectors by lazy {
        stateFilter.filter(
            source.allInnerSelectors.filter {
                isStaticSelector(it) && filter(it)
            },
            state
        )
    }

    override val dynamicSelectors by lazy {
        stateFilter.filter(
            source.allInnerSelectors.filter {
                !isStaticSelector(it)
                    && !it.hasAnnotation(UI.Widgets.ValidationError::class)
                    && !it.hasAnyParentAnnotation(UI.Widgets.ValidationError::class)
                    && filter(it)
                },
            state
        )
    }

    private fun filter(it: BaseSelector): Boolean {
        return it.field!!.kotlinProperty!!.visibility == KVisibility.PUBLIC
    }

    private fun isStaticSelector(it: BaseSelector): Boolean {
        return !it.hasAnnotation(UI.Visibility.Dynamic::class)
                && !it.hasAnnotation(UI.Visibility.Backend::class)
                && !it.hasAnyParentAnnotation(UI.Visibility.Backend::class)
                && !it.hasAnyParentAnnotation(UI.Visibility.Dynamic::class)
                && !it.hasAnyParentAnnotation(UI.Widgets.ValidationError::class)
                && !it.hasAnnotation(UI.Widgets.ValidationError::class)
                && !it.hasAnnotation(UI.Widgets.OptionItem::class)
    }
}