package org.xpathqs.demo.util

import io.qameta.allure.Epic
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.framework.base.BaseUiTest
import org.xpathqs.framework.pom.Page

@Epic("UI-Test")
open class UiTest(
    startUpPage: Page? = HomePage,
    redirectPage: Page? = null,
    afterDriverCreated: (BaseUiTest.()->Unit)? = null,
) : BaseUiTest(
    startUpPage = startUpPage,
    redirectPage = redirectPage,
    afterDriverCreated = afterDriverCreated
)