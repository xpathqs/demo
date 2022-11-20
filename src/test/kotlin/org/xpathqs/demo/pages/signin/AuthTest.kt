package org.xpathqs.demo.pages.signin

import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.testng.annotations.Test
import org.xpathqs.core.selector.block.Block
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignInPage
import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.util.UiTest
import org.xpathqs.driver.extensions.click
import org.xpathqs.framework.*
import org.xpathqs.framework.extensions.*
import org.xpathqs.gwt.WHEN
import org.xpathqs.web.extensions.submit

@Feature("SignIn")
@Story("Functional")
class AuthTest : UiTest(
    afterDriverCreated = { SignInPage.navigate(STATE_UNAUTHORIZED) }
) {
    @Test
    fun `Success authentication`() {
        WHEN("User authenticate with correct credentials") {
            SignInPage().submit {
                login = "test@test.com"
                password = "test"
            }
        }.THEN("HomePage And Header should be shown with the elements for the authorized user") {
            val selectors = (HomePage as Block).getStaticSelectorsWithState(STATE_AUTHORIZED)
            selectors.forEach {
                it should beVisible
            }
        }
    }

  /*  @Test
    fun `Incorrect credentials`() {
        WHEN("User authenticate with incorrect credentials") {
            SignInPage().fill {
                login = "test@test.com"
                password = "1"
            }
            SignInPage.signIn.click()
        }.THEN("HomePage And Header should be shown with the elements for the authorized user") {
            val selectors = (HomePage as Block).getStaticSelectorsWithState(STATE_AUTHORIZED)
            selectors.forEach {
                it should beVisible
            }
        }
    }*/
}