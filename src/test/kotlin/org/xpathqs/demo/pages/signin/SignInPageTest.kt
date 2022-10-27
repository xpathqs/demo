package org.xpathqs.demo.pages.signin

import io.qameta.allure.Feature
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignInPage
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.util.PageTest
import org.xpathqs.framework.base.BasePageTest

@Feature("SignIn")
class SignInPageTest : PageTest(
    page = SignInPage,
    state = GlobalState.STATE_UNAUTHORIZED
) {
}