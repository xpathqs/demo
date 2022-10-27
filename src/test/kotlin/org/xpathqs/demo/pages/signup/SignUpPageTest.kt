package org.xpathqs.demo.pages.signup

import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignUpPage
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest

class SignUpPageTest : PageTest(
    page = SignUpPage,
    state = GlobalState.STATE_UNAUTHORIZED
){

}
