package org.xpathqs.demo.pages

import io.qameta.allure.Feature
import org.xpathqs.demo.util.base.BasePageTest

@Feature("HomePage")
class HomePageTest : BasePageTest(page = HomePage, startUpPage = HomePage) {

}