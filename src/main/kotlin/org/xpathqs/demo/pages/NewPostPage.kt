package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.driver.model.IBaseModel
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IModelBlock
import org.xpathqs.framework.pom.Page
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = STATE_AUTHORIZED, pageState = STATE_AUTHORIZED)
@UI.Nav.Config(STATE_AUTHORIZED)
object NewPostPage : Page(), IModelBlock<NewPostPage.NewPostModel> {
    val articleTitle = HTML.input(placeHolder = "Article Title")
    val description = HTML.input(placeHolder = "What's this article about?")
    val content = HTML.textarea()
    val tags = HTML.input(placeHolder = "Enter tags")

    val publish = HTML.button(text = "Publish Article")

    class NewPostModel : IBaseModel(NewPostPage) {
        var articleTitle by Fields.input()
        var description by Fields.input()
        var content by Fields.input()
        var tags by Fields.input()
    }

    override fun invoke() = NewPostModel()
}