package org.xpathqs.demo.pages

import io.qameta.allure.Feature
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest

@Feature("Profile")
class ProfilePageTest : PageTest(
    page = ProfilePage,
    state = GlobalState.STATE_AUTHORIZED
)