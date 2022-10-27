package org.xpathqs.demo.pages

import org.xpathqs.demo.pages.common.GlobalState
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.driver.model.IBaseModel
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IModelBlock
import org.xpathqs.framework.pom.Page
import org.xpathqs.framework.setup
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(
    weight = 1000,
    bySubmit = HomePage::class,
    modelState = IBaseModel.DEFAULT,
    pageState = GlobalState.STATE_AUTHORIZED,
    selfPageState = GlobalState.STATE_UNAUTHORIZED
)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = GlobalState.STATE_UNAUTHORIZED, pageState = GlobalState.STATE_UNAUTHORIZED)
@UI.Nav.Config(defaultState = GlobalState.STATE_UNAUTHORIZED)
object SignUpPage : Page(), IModelBlock<SignUpPage.SignUpModel> {
    @UI.Nav.DeterminateBy
    val pageTitle = HTML.h1(text = "Sign up")

    val login = HTML.input(placeHolder = "Your Name")
    val email = HTML.input(placeHolder = "Email")
    val password = HTML.secretInput(placeHolder = "Password")

    @UI.Widgets.Submit
    val signUp = HTML.button(text = "Sign up")

    class SignUpModel : IBaseModel(SignUpPage) {
        var login by Fields.input(SignUpPage.login, default = "t1")
        var email by Fields.input(SignUpPage.email, default = "t1@test.com")
        var password by Fields.input(SignUpPage.password, default = "1")

        override val states by lazy {
            runInModel {
                mapOf(
                    DEFAULT to SignUpModel().apply {
                        login = "test@test.com"
                        email = "test@test.com"
                        password = "test"
                    },

                    INCORRECT to SignUpModel().apply {
                        login = "test@test.com"
                        email = "test@test.com"
                        password = "1"
                    },
                )
            }
        }
    }

    override fun invoke() :SignUpModel {
        return SignUpModel().states[IBaseModel.DEFAULT]!!.setup {
            val rnd = System.currentTimeMillis().toString().takeLast(7)
            login = "l_$rnd@test.com"
            email = "e_$rnd@test.com"
            password = "test"
        }
    }
}

fun SignUpPage.SignUpModel.toSignInModel(): SignInPage.SignInModel {
    val model = this
    return SignInPage().applyModel {
        login = model.email
        password = model.password
    }
}