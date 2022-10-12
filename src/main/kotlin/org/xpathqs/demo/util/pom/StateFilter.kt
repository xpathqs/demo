package org.xpathqs.demo.util.pom

import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.findAnyParentAnnotation
import org.xpathqs.driver.navigation.annotations.UI

class StateFilter: IStateFilter {
    override fun filter(col: Collection<BaseSelector>, state: Int): Collection<BaseSelector> {
        return if(state != UI.Visibility.UNDEF_STATE) {
            col.filter {
                val st = it.findAnyParentAnnotation<UI.Visibility.State>()
                st == null || state == st.value
            }
        } else {
            col
        }
    }
}