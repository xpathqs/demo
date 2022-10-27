package org.xpathqs.demo.pages.home

import io.qameta.allure.Feature
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest

@Feature("HomePage/Authorized")
class HomePageAuthTest : PageTest(
    page = HomePage,
    state = STATE_AUTHORIZED
) {

}