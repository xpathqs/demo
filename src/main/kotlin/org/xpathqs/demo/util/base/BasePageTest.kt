package org.xpathqs.demo.util.base

import io.qameta.allure.Story
import org.testng.ITest
import org.testng.SkipException
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.demo.util.pom.*
import org.xpathqs.driver.log.Log
import org.xpathqs.driver.navigation.annotations.UI.Visibility.Companion.UNDEF_STATE
import org.xpathqs.driver.navigation.base.IModelBlock
import org.xpathqs.log.style.StyleFactory
import java.lang.reflect.Method

open class BasePageTest(
    protected val page: Page,
    protected val state: Int = UNDEF_STATE,
    protected val modelBlock: IModelBlock<*>? = page as? IModelBlock<*>,
    startUpPage: Page,
    redirectPage: Page? = null,
    private val allurePrefix: String = "",
    protected val checker: ISelectorCheck = SelectorCheck(),
    protected val extractor: ISelectorExtractor = SelectorExtractor(page, state),

    protected val validationExtractor: IValidationExtractor = ValidationExtractor(modelBlock),
   /* protected val validationCheck: IValidationCheck = object: ValidationCheck() {
        override val model: IBaseModel?
            get() {
                return modelBlock?.invoke()
            }
    },*/
    protected var stateHolder: IPageStateHolder? = null,
    afterDriverCreated: (BaseUiTest.()->Unit)? = null
) : BaseUiTest(
    startUpPage = startUpPage,
    redirectPage = redirectPage,
    afterDriverCreated = afterDriverCreated
), ITest {

   /* override fun precondition() {
        if(validationCheck.model != null) {
            stateHolder = PageStateHolder(validationCheck.model!!.apply { default() })
            (validationCheck as ValidationCheck).stateHolder = stateHolder
        }
    }*/


    @Story("Elements/Static")
    @Test(dataProvider = "getStatic")
    fun testStatic(sel: BaseSelector) {
        checker.checkSelector(sel)
    }

    @Story("Элементы/Динамические")
    @Test(dataProvider = "getDynamic")
    fun testDynamic(sel: BaseSelector) {
        checker.checkSelector(sel)
    }

/*

    @Story("Правила валидации")
    @Test(dataProvider = "getValidations")
    fun testValidations(tc: ValidationTc) {
        validationCheck.checkValidation(tc)
    }
*/

    @DataProvider
    fun getStatic() = extractor.staticSelectors.toTypedArray()

    @DataProvider
    fun getDynamic() = extractor.dynamicSelectors.toTypedArray()

    @DataProvider
    fun getValidations() = validationExtractor.validations.toTypedArray()

    companion object {
        val testCaseName = ThreadLocal<String>()
    }

    override fun getTestName(): String {
        return testCaseName.get() ?: ""
    }

    @BeforeMethod(alwaysRun = true)
    open fun setTestName(method: Method, args: Array<Any?>?) {
        if(args?.isNotEmpty() == true) {
            val sel = args!![0] as? BaseSelector
            if(sel != null) {
                testCaseName.set(sel.name)
            } else {
                val tc = args[0] as? ValidationTc
                if(tc != null) {
                    val title = "Валидация для поля '${tc.v.prop.name}' с типом '${tc.rule}'"
                    testCaseName.set(title)
                    Log.tag(
                        StyleFactory.testTitle("                    $title                    "), "title"
                    )
                } else {
                    testCaseName.set(method.name)
                }
            }
        } else {
            testCaseName.set(method.name)
        }
    }

    @BeforeClass
    fun navigate() {
        try {
            page.navigate(state)
        } catch (e: Exception) {
            e.printStackTrace()
            throw SkipException("Navigation was not complited")
        }
    }
}