package org.xpathqs.demo.pages.common

import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.demo.pages.SignInPage
import org.xpathqs.demo.util.pom.Block
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IPageStateDelegate
import org.xpathqs.driver.navigation.impl.PageState
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
object Header : Block(HTML.header()), IPageStateDelegate {
    const val STATE_UNAUTHORIZED = 1
    const val STATE_AUTHORIZED = 2

    @UI.Nav.WaitForLoad
    val home = HTML.li(innerText = "Home")

    @UI.Visibility.State(STATE_UNAUTHORIZED)
    @UI.Visibility.Backend
    object Unuathorized : Block() {
        @UI.Nav.PathTo(byClick = SignInPage::class)
        val signIn = HTML.li(innerText = "Sign in")
        val signUp = HTML.li(innerText = "Sign up")
    }

    @UI.Visibility.State(STATE_AUTHORIZED)
    @UI.Visibility.Backend
    object Authorized : Block() {
        val newPost = HTML.li(innerText = "New Post")
        val settings = HTML.li(innerText = "Settings")
        val profileSummary = HTML.li()["last()"]
    }

    override val ps = PageState(this)
}