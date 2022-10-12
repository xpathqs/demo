package org.xpathqs.demo.util

import org.xpathqs.core.reflection.PackageScanner
import org.xpathqs.demo.util.pom.IbNavigator
import org.xpathqs.driver.log.Log
import org.xpathqs.driver.navigation.Navigator

object UiInitializer {
    private var isInit = ThreadLocal<Boolean>()
    init {
        isInit.set(false)
    }
    fun initNavigations() {
        synchronized(this) {
            val tid = Thread.currentThread().toString()

            if(isInit.get() == true) return@initNavigations

            val navigators =
                listOf(
                    IbNavigator,
                )
            navigators.forEach {
                if(it.navigator == null) {
                    it.navigator.set(Navigator())
                    if(it is IbNavigator) {
                        Log.info("[$tid] Navigator created: " + it.navigator.get())
                    }
                }
            }

            PackageScanner("org.xpathqs.demo.pages")
                .scan()

            navigators.forEach {
                it.navigator.get().initNavigations()
            }

            isInit.set(true)
        }
    }
}