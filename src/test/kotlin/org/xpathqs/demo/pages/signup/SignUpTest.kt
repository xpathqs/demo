package org.xpathqs.demo.pages.signup

import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.testng.annotations.Test
import org.xpathqs.demo.pages.SignInPage
import org.xpathqs.demo.pages.SignUpPage
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.demo.pages.toSignInModel
import org.xpathqs.demo.util.UiTest
import org.xpathqs.framework.extensions.beVisible
import org.xpathqs.framework.extensions.should
import org.xpathqs.framework.log.ScreenshotConfig
import org.xpathqs.gwt.AND
import org.xpathqs.gwt.GIVEN

@Feature("SignUp")
class SignUpTest : UiTest(
    afterDriverCreated = { SignInPage.navigate(GlobalState.STATE_UNAUTHORIZED) }
){

    @Test
    @Story("Functional")
    @ScreenshotConfig(
        afterWhen = true,
        afterThen = true,
        actionInWhen = true
    )
    fun `Register new user`() {
        GIVEN("Create a new user model") {
            SignUpPage()
        }.WHEN("Attempt to SignIn under unregistered user model") {
            given.toSignInModel().submit(false)
        }.THEN("Login Error should be shown") {
            SignInPage.Errors.incorrectCredentials should beVisible
        }.AND("Sign Up provided with model") {
            given.submit()
        }.THEN("User should be registered") {
            Header.Authorized.newPost should beVisible
        }
    }

}