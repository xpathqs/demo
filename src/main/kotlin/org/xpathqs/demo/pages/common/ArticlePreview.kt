package org.xpathqs.demo.pages.common

import org.xpathqs.core.util.SelectorFactory
import org.xpathqs.demo.util.pom.Block
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.web.factory.HTML

class ArticlePreview : Block(
    HTML.div(cls = "article-preview")
) {
    @UI.Visibility.Backend
    val emptyBlock = SelectorFactory.textSelector("No articles are here... yet.")

    @UI.Visibility.Backend
    inner class Content : Block() {
        inner class Meta : Block(
            HTML.div(cls = "article-meta")
        ) {
            val author = HTML.a(cls = "author")
            val date = HTML.span(cls = "date")
            val buttonLike = HTML.button()
        } val meta = Meta()

        inner class Preview : Block(
            HTML.a(cls = "preview-link")
        ) {
            val title = HTML.h1()
            val content = HTML.p()
            val readMore = HTML.span(text = "Read more...")
        } val preview = Preview()

        inner class Tags : Block(
            HTML.ul(cls = "tag-list")
        ) {
            val tagName = HTML.li()
        } val tags = Tags()
    } val content = Content()

}