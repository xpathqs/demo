package org.xpathqs.demo.util.pom

import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.block.Block
import org.xpathqs.demo.util.widgets.ISelectorNav
import org.xpathqs.driver.extensions.isHidden
import org.xpathqs.driver.navigation.base.*
import org.xpathqs.driver.navigation.impl.Loadable
import org.xpathqs.driver.navigation.impl.Navigable
import org.xpathqs.driver.navigation.impl.NavigableDetermination
import org.xpathqs.driver.navigation.util.BlockNavigation
import org.xpathqs.driver.navigation.util.IBlockNavigation
import org.xpathqs.driver.navigation.util.NullBlockNavigation

open class Block(
) : Block(),
    INavigableDeterminationDelegate,
    ILoadableDelegate,
    IBlockNavigation, IBlockSelectorNavigation, IXpathQsNavigator
{
    constructor(sel: ISelector) : this() {
        copyFrom(sel)
    }

    override fun afterReflectionParse() {
        val pcg = this::class.java.packageName
        if(!pcg.startsWith("org.xpathqs.demo.util")) {
            navigator.register(this)
        }
    }

    override val nav: NavigableDetermination
        get() = NavigableDetermination(navigator, this)

    override val loadable: ILoadable
            = Loadable(this)

    override val selfNavigation: BlockNavigation
        get() = NullBlockNavigation()

    override fun navigate(elem: ISelector, navigator: INavigator) {
        if(elem is BaseSelector) {
            if(elem.base is ISelectorNav) {
                (elem.base as ISelectorNav).navigateDirectly(elem)
            }
            if(elem.isHidden) {
                (nav as Navigable).navigate(elem, navigator)
            }
        }
    }
}