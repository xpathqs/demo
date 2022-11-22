package org.xpathqs.demo.pages

import org.xpathqs.core.selector.extensions.contains
import org.xpathqs.core.selector.extensions.containsAny
import org.xpathqs.core.selector.extensions.parentCount

import org.xpathqs.demo.pages.common.ArticlePreview
import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.driver.extensions.afterAction
import org.xpathqs.driver.extensions.isVisible
import org.xpathqs.driver.extensions.waitForVisible
import org.xpathqs.driver.navigation.annotations.NavOrderType
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IPageState
import org.xpathqs.framework.pom.Block
import org.xpathqs.framework.pom.Page
import org.xpathqs.web.factory.HTML
import java.time.Duration

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = STATE_UNAUTHORIZED, pageState = STATE_UNAUTHORIZED)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = STATE_AUTHORIZED, pageState = STATE_AUTHORIZED)
@UI.Nav.Config(defaultState = STATE_UNAUTHORIZED)
@UI.Nav.Order(type = NavOrderType.LOWEST)
object HomePage : Page(), IPageState {
    override val url: String
        get() = "http://localhost:3000/"

    @UI.Nav.DeterminateBy
    object Content : Block(
        HTML.div(cls = "home-page")
    ) {
        @UI.Visibility.State(STATE_UNAUTHORIZED)
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
                @UI.Visibility.State(STATE_AUTHORIZED)
                @UI.Visibility.Dynamic
                @UI.Nav.PathTo(byClick = YourFeed::class)
                val yourFeed = HTML.a(text = "Your Feed").afterAction {
                    YourFeed.article.waitForVisible(Duration.ofSeconds(5))
                }

                @UI.Nav.PathTo(byClick = Global::class)
                val global = HTML.a(text = "Global Feed").afterAction {
                    Global.article.waitForVisible(Duration.ofSeconds(5))
                }
            }

            @UI.Visibility.Dynamic
            @UI.Visibility.State(STATE_AUTHORIZED)
            object YourFeed : Block(
                (HTML.div(cls = "feed-toggle") containsAny HTML.a(
                    clsContains = "active",
                    text = "Your Feed"
                )).parentCount(1)
            ) {
                val article = ArticlePreview()
            }

            @UI.Visibility.Dynamic
            object Global : Block(
                (HTML.div(cls = "feed-toggle") containsAny HTML.a(
                    clsContains = "active",
                    text = "Global Feed"
                )).parentCount(1)
            ) {
                val article = ArticlePreview()
            }
        }

        @UI.Visibility.Backend
        object Tags : Block(HTML.div(cls = "sidebar")) {
            @UI.Nav.WaitForLoad
            val caption = HTML.p(text = "Popular Tags")
            val tagItem = HTML.a(clsContains = "tag-pill")
        }
    }

    override val pageState: Int
        get() = if (Content.Banner.title.isVisible) STATE_UNAUTHORIZED else STATE_AUTHORIZED
}