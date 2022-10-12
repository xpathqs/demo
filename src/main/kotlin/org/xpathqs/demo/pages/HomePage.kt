package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.Header
import org.xpathqs.demo.util.pom.Block
import org.xpathqs.demo.util.pom.Page
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(contains = [Header::class])
object HomePage : Page() {
    override val url: String
        get() = "http://localhost:3000/"

    @UI.Nav.DeterminateBy
    object Content : Block(HTML.div(cls = "home-page")) {
        object Banner : Block(HTML.div(cls = "banner")) {
            val title = HTML.h1(text = "conduit")
            val subtitle = HTML.p(text = "A place to share your knowledge.")
        }
    }
}