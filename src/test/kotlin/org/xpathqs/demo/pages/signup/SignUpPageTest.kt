package org.xpathqs.demo.pages.signup

import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignUpPage
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.base.BasePageTest

class SignUpPageTest : BasePageTest(
    page = SignUpPage,
    startUpPage = HomePage,
    state = GlobalState.STATE_UNAUTHORIZED
){

}
