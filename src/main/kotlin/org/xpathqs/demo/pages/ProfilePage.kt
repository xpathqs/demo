package org.xpathqs.demo.pages

import org.xpathqs.core.selector.extensions.containsAny
import org.xpathqs.core.selector.extensions.parentCount
import org.xpathqs.demo.pages.common.ArticlePreview
import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.demo.util.pom.Block
import org.xpathqs.demo.util.pom.Page
import org.xpathqs.driver.extensions.afterAction
import org.xpathqs.driver.extensions.waitForVisible
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.web.factory.HTML
import java.time.Duration

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = GlobalState.STATE_AUTHORIZED, pageState = GlobalState.STATE_AUTHORIZED)
@UI.Nav.Config(GlobalState.STATE_AUTHORIZED)
object ProfilePage : Page(
   HTML.div(cls = "profile-page")
) {
    @UI.Nav.WaitForLoad
    object UserInfo : Block(
        HTML.div(cls = "user-info")
    ) {
        @UI.Nav.DeterminateBy
        val userName = HTML.h4()
        val editSettings = HTML.a(testId = "edit-settings")
    }

    object Articles: Block(
        HTML.div(cls = "container")
    ) {
        object Tabs : Block(
            HTML.div(cls = "articles-toggle")
        ) {
            @UI.Visibility.Backend
            @UI.Nav.PathTo(byClick = MyArticles::class)
            val myArticles = HTML.a(text = "My Articles").afterAction {
                MyArticles.article.waitForVisible(Duration.ofSeconds(5))
            }

            @UI.Nav.PathTo(byClick = Favorited::class)
            val favorited = HTML.a(text = "Favorited Articles").afterAction {
                Favorited.article.waitForVisible(Duration.ofSeconds(5))
            }
        }

        @UI.Visibility.Dynamic
        @UI.Visibility.State(GlobalState.STATE_AUTHORIZED)
        object MyArticles : Block(
            (HTML.div(cls = "articles-toggle") containsAny HTML.a(clsContains = "active", text = "My Articles")).parentCount(1)
        ) {
            val article = ArticlePreview()
        }

        @UI.Visibility.Dynamic
        object Favorited : Block(
            (HTML.div(cls = "articles-toggle") containsAny HTML.a(clsContains = "active", text = "Favorited Articles")).parentCount(1)
        ){
            val article = ArticlePreview()
        }
    }
}