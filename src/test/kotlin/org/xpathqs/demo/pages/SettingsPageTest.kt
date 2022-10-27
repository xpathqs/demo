package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest


class SettingsPageTest : PageTest(
    page = SettingsPage,
    state = GlobalState.STATE_AUTHORIZED
){

}
