package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest


class NewPostPageTest : PageTest(
    page = NewPostPage,
    state = GlobalState.STATE_AUTHORIZED
){

}
