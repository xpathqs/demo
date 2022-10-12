package org.xpathqs.demo.util.pom

import org.xpathqs.driver.navigation.Navigator

interface IXpathQsNavigator {
    val navigator: Navigator
        get() {
            return IbNavigator.navigator.get()
        }
}