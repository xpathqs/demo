package org.xpathqs.demo.pages

import org.xpathqs.core.util.SelectorFactory.textSelector
import org.xpathqs.demo.pages.common.GlobalState.STATE_AUTHORIZED
import org.xpathqs.demo.pages.common.GlobalState.STATE_UNAUTHORIZED
import org.xpathqs.demo.pages.common.Header
import org.xpathqs.driver.model.IBaseModel
import org.xpathqs.driver.model.IBaseModel.Companion.DEFAULT
import org.xpathqs.driver.model.IBaseModel.Companion.INCORRECT
import org.xpathqs.driver.navigation.annotations.UI
import org.xpathqs.driver.navigation.base.IModelBlock
import org.xpathqs.framework.pom.Block
import org.xpathqs.framework.pom.Page
import org.xpathqs.framework.validation.*
import org.xpathqs.framework.widgets.UIKit
import org.xpathqs.web.factory.HTML

@OptIn(ExperimentalStdlibApi::class)
@UI.Nav.PathTo(
    bySubmit = HomePage::class,
    modelState = DEFAULT,
    pageState = STATE_AUTHORIZED,
    selfPageState = STATE_UNAUTHORIZED
)
@UI.Nav.PathTo(contains = [Header::class], selfPageState = STATE_UNAUTHORIZED, pageState = STATE_UNAUTHORIZED)
@UI.Nav.Config(defaultState = STATE_UNAUTHORIZED)
@UI.Visibility.State(STATE_UNAUTHORIZED)
object SignInPage : Page(), IModelBlock<SignInPage.SignInModel> {
    @UI.Nav.DeterminateBy
    val pageTitle = HTML.h1(text = "Sign in")

    val login = UIKit.input("Email")
    val password = UIKit.input("Password")

    @UI.Widgets.Submit
    val signIn = HTML.button(text = "Sign in")

    object Errors : Block() {
        @UI.Visibility.Dynamic(modelState = INCORRECT, submitModel = true)
        val incorrectCredentials = textSelector("email or password is invalid")
    }

    class SignInModel : IBaseModel(SignInPage), IValidationModel<SignInModel> {
        var login by Fields.input(SignInPage.login, default = "t1@test.com")
        var password by Fields.input(SignInPage.password, default = "1")

        override val validations: Validations<SignInModel>
            get() = validationFor(ValidationConfig(InvalidationAction.SUBMIT)) {
                SignInModel::login {
                    required() hint "This field is required"
                }
                SignInModel::password {
                    required() hint "This field is required"
                }
            }

        override val states by lazy {
            runInModel {
                mapOf(
                    DEFAULT to SignInModel().apply {
                        login = "test@test.com"
                        password = "test"
                    },

                    INCORRECT to SignInModel().apply {
                        login = "test@test.com"
                        password = "1"
                    },
                )
            }
        }
    }

    override fun invoke() = SignInModel()
}