package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.demo.util.pom.Page
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = GlobalState.STATE_AUTHORIZED, pageState = GlobalState.STATE_AUTHORIZED)
@UI.Nav.Config(GlobalState.STATE_AUTHORIZED)
object SettingsPage : Page() {

    @UI.Nav.DeterminateBy
    val header = HTML.h1(text = "Your Settings")

    val urlInput = HTML.input(placeHolder = "URL of profile picture")
    val nameInput = HTML.input(placeHolder = "Your Name")
    val email = HTML.input(placeHolder = "Email")
    val password = HTML.secretInput(placeHolder = "Password")

    @UI.Widgets.Submit
    val update = HTML.button(text = "Update Settings")

    @UI.Nav.PathTo(byClick = HomePage::class, selfPageState = GlobalState.STATE_AUTHORIZED, pageState = GlobalState.STATE_UNAUTHORIZED)
    val logout = HTML.button(text = "Or click here to logout.")
}