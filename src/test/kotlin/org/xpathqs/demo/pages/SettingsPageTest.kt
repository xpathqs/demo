package org.xpathqs.demo.pages

import io.qameta.allure.Feature
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest

@Feature("Settings")
class SettingsPageTest : PageTest(
    page = SettingsPage,
    state = GlobalState.STATE_AUTHORIZED
)
