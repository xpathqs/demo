package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.base.BasePageTest


class NewPostPageTest : BasePageTest(
    page = NewPostPage,
    startUpPage = HomePage,
    state = GlobalState.STATE_AUTHORIZED
){

}
