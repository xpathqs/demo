package org.xpathqs.demo.pages.home

import io.qameta.allure.Feature
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.util.base.BasePageTest

@Feature("HomePage/Unauthorized")
class HomePageUnauthTest : BasePageTest(
    page = HomePage,
    startUpPage = HomePage,
    state = STATE_UNAUTHORIZED
) {

}