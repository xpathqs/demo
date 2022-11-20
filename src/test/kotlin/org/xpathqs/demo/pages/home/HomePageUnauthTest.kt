package org.xpathqs.demo.pages.home

import io.qameta.allure.Feature
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.annotation.TestGeneratorConfig

/*@TestGeneratorConfig(
    enableStaticSelectors = true
)*/
@Feature("HomePage/Unauthorized")
class HomePageUnauthTest : PageTest(
    page = HomePage,
    state = STATE_UNAUTHORIZED
)