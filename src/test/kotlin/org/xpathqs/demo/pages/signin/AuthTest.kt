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
import org.xpathqs.framework.base.BaseUiTest
import org.xpathqs.framework.getStaticSelectorsWithState
import org.xpathqs.framework.submit
import org.xpathqs.framework.бытьВидимым
import org.xpathqs.framework.должен
import org.xpathqs.gwt.WHEN

@Feature("SignIn")
class AuthTest : UiTest(
    afterDriverCreated = { SignInPage.navigate(STATE_UNAUTHORIZED) }
) {
    @Story("Functional")
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
                it должен бытьВидимым
            }
        }
    }
}