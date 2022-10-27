package org.xpathqs.demo.pages.signup

import org.testng.annotations.Test
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignInPage
import org.xpathqs.demo.pages.SignUpPage
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.demo.pages.toSignInModel
import org.xpathqs.demo.util.PageTest
import org.xpathqs.demo.util.UiTest
import org.xpathqs.framework.base.BaseUiTest
import org.xpathqs.framework.log.ScreenshotConfig
import org.xpathqs.framework.бытьВидимым
import org.xpathqs.framework.должен
import org.xpathqs.gwt.AND
import org.xpathqs.gwt.GIVEN

class SignUpTest : UiTest(
    afterDriverCreated = { SignInPage.navigate(GlobalState.STATE_UNAUTHORIZED) }
){

    @Test
    @ScreenshotConfig(
        afterWhen = true,
        afterThen = true,
        actionInWhen = true
    )
    fun test1() {
        GIVEN("Create a new user model") {
            SignUpPage()
        }.WHEN("Attempt to SignIn under unregistered user model") {
            given.toSignInModel().submit(false)
        }.THEN("Login Error should be shown") {
            SignInPage.Errors.incorrectCredentials должен бытьВидимым
        }.AND("Sign Up provided with model") {
            given.submit()
        }.THEN("User should be registered") {
            Header.Authorized.newPost должен бытьВидимым
        }
    }

}