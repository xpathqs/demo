package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.base.BasePageTest


class ProfilePageTest : BasePageTest(
    page = ProfilePage,
    startUpPage = HomePage,
    state = GlobalState.STATE_AUTHORIZED
){

}
