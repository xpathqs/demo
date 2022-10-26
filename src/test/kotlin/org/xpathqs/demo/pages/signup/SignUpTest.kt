package org.xpathqs.demo.pages.signup

import org.testng.annotations.Test
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignInPage
import org.xpathqs.demo.pages.SignUpPage
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.demo.pages.toSignInModel
import org.xpathqs.demo.util.base.BaseUiTest
import org.xpathqs.demo.util.log.ScreenshotConfig
import org.xpathqs.demo.util.бытьВидимым
import org.xpathqs.demo.util.должен
import org.xpathqs.gwt.AND
import org.xpathqs.gwt.GIVEN

class SignUpTest : BaseUiTest(
    startUpPage = HomePage,
    afterDriverCreated = { SignInPage.navigate(GlobalState.STATE_UNAUTHORIZED) }
){

    fun tt() {
        SignInPage().login = "asd"
        println()
    }

    @Test
    @ScreenshotConfig(
        afterWhen = true,
        afterThen = true,
        actionInWhen = true
    )
    fun test1() {
        GIVEN("Create a new user model") {
            SignUpPage()
        }.WHEN("Attempt to SignIn under this model (unregistered user)") {
            given.toSignInModel().submit(false)
        }.THEN("Login Error should be shown") {
            SignInPage.Errors.incorrectCredentials должен бытьВидимым
        }.AND("Sign Up provided with model") {
            given.submit()
        }.THEN("User should be registred") {
            Header.Authorized.newPost должен бытьВидимым
        }
    }

}