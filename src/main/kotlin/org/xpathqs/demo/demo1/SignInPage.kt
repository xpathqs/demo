package org.xpathqs.demo.demo1

import org.xpathqs.core.selector.extensions.contains
import org.xpathqs.core.util.SelectorFactory.textSelector
import org.xpathqs.demo.pages.HomePage
import org.xpathqs.demo.pages.SignUpPage
import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.util.pom.Block
import org.xpathqs.demo.util.pom.Page
import org.xpathqs.demo.util.validation.*
import org.xpathqs.demo.util.widgets.UIKit
import org.xpathqs.driver.extensions.afterAction
import org.xpathqs.driver.extensions.waitForVisible
import org.xpathqs.driver.model.IBaseModel
import org.xpathqs.driver.model.IBaseModel.Companion.DEFAULT
import org.xpathqs.driver.model.IBaseModel.Companion.INCORRECT
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IModelBlock
import org.xpathqs.driver.navigation.base.IPageState
import org.xpathqs.web.factory.HTML
import java.time.Duration

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(contains = [Header::class])
object SignInPage : Page() {
    val pageTitle = HTML.h1(text = "Sign in")

    val login = HTML.input(placeHolder = "Email")
    val password = HTML.input(placeHolder = "Password")

    val signIn = HTML.button(text = "Sign in")
}

@OptIn(ExperimentalStdlibApi::class)
object Header : Block() {
    @UI.Nav.PathTo(byClick = HomePage::class)
    val home = HTML.li(innerText = "Home")

    @UI.Nav.PathTo(byClick = SignInPage::class)
    val signIn = HTML.li(innerText = "Sign in")

    @UI.Nav.PathTo(byClick = SignUpPage::class)
    val signUp = HTML.li(innerText = "Sign up")
}

object SignUp
object HomePage : Page() {
    override val url: String
        get() = "http://localhost:3000/"

    object Content : Block(
        HTML.div(cls = "home-page")
    ) {
        object Banner : Block(HTML.div(cls = "banner")) {
            val title = HTML.h1(text = "conduit")
            val subtitle = HTML.p(text = "A place to share your knowledge.")
        }

        object Feed : Block(
            HTML.div(cls = "col-md-9") contains HTML.div(cls = "feed-toggle")
        ) {
            object Tabs : Block(
                HTML.div(cls = "feed-toggle")
            ) {
                val yourFeed = HTML.a(text = "Your Feed")
                val global = HTML.a(text = "Global Feed")
            }
        }
    }
}


fun main() {
    SignInPage.pageTitle
    SignInPage.password
    SignInPage.login
    SignInPage.signIn
  //  SignInPage.Errors.incorrectCredentials

    Header.home
    Header.signIn
    Header.signUp
}

object Errors: Block(
    HTML.ul(cls = "error-messages")
) {
    val incorrectCredentials = textSelector("email or password is invalid")
}