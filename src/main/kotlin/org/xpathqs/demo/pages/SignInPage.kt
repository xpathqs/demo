package org.xpathqs.demo.pages

import org.xpathqs.demo.util.pom.Page
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.web.factory.HTML

object SignInPage : Page() {
    @UI.Nav.DeterminateBy
    val pageTitle = HTML.h1(text = "Sign in")

    val login = HTML.input(placeHolder = "Email")
    val password = HTML.secretInput(placeHolder = "Password")

    @UI.Widgets.Submit
    val signIn = HTML.button(text = "Sign in")
}