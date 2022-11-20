package org.xpathqs.demo.util

import org.xpathqs.demo.pages.HomePage
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.framework.base.BasePageTest
import org.xpathqs.framework.base.BaseUiTest
import org.xpathqs.framework.pom.Page

open class PageTest(
    page: Page,
    state: Int = UI.Visibility.UNDEF_STATE,
    startUpPage: Page = HomePage,
    redirectPage: Page? = null,

    afterDriverCreated: (BaseUiTest.()->Unit)? = null,
) : BasePageTest(
    page = page,
    state = state,
    startUpPage = startUpPage,
    redirectPage = redirectPage,
    afterDriverCreated = afterDriverCreated,
)