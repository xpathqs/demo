package org.xpathqs.demo.pages.common

import org.xpathqs.demo.pages.*
import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.util.pom.Block
import org.xpathqs.driver.extensions.isVisible
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IPageState
import org.xpathqs.driver.navigation.base.IPageStateDelegate
import org.xpathqs.driver.navigation.impl.PageState
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
object Header : Block(HTML.header()), IPageState {

    @UI.Visibility.State(STATE_UNAUTHORIZED)
    object Unuathorized : Block() {
        @UI.Nav.PathTo(byClick = HomePage::class, pageState = STATE_UNAUTHORIZED, selfPageState = STATE_UNAUTHORIZED)
        val home = HTML.li(innerText = "Home")

        @UI.Nav.PathTo(byClick = SignInPage::class, pageState = STATE_UNAUTHORIZED, selfPageState = STATE_UNAUTHORIZED)
        val signIn = HTML.li(innerText = "Sign in")

        @UI.Nav.PathTo(byClick = SignUpPage::class, pageState = STATE_UNAUTHORIZED, selfPageState = STATE_UNAUTHORIZED)
        val signUp = HTML.li(innerText = "Sign up")
    }

    @UI.Visibility.State(STATE_AUTHORIZED)
    object Authorized : Block() {
        @UI.Nav.PathTo(byClick = HomePage::class, pageState = STATE_AUTHORIZED, selfPageState = STATE_AUTHORIZED)
        val home = HTML.li(innerText = "Home")

        @UI.Nav.PathTo(byClick = NewPostPage::class, pageState = STATE_AUTHORIZED, selfPageState = STATE_AUTHORIZED)
        val newPost = HTML.a(testId = "newpost")

        @UI.Nav.PathTo(byClick = SettingsPage::class, pageState = STATE_AUTHORIZED, selfPageState = STATE_AUTHORIZED)
        val settings = HTML.a(testId = "settings")

        @UI.Nav.PathTo(byClick = ProfilePage::class, pageState = STATE_AUTHORIZED, selfPageState = STATE_AUTHORIZED)
        val profileSummary = HTML.a(testId = "username")
    }

    override val pageState: Int
        get() = if(Unuathorized.signIn.isVisible) STATE_UNAUTHORIZED else STATE_AUTHORIZED
//    override val ps = PageState(this)
}