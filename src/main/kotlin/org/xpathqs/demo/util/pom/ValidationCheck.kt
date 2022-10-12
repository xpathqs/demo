package org.xpathqs.demo.util.pom

import org.xpathqs.driver.model.IBaseModel

interface IValidationCheck {
    val model: IBaseModel?
    fun checkValidation(tc: ValidationTc)
}

abstract class ValidationCheck(
    private val takeScreenshot: Boolean = true,
    var stateHolder: IPageStateHolder? = null
) : IValidationCheck {

   /* lateinit var uiModel: IBaseModel
    var methodCalled = false
    var applyModels = HashSet<IBaseModel>()

    override fun checkValidation(tc: ValidationTc) {
        Allure.getLifecycle().updateTestCase {
            it.name = "Валидация для поля '${tc.v.prop.name}' с типом '${tc.rule}'"
        }

        stateHolder!!.save()
        val model = tc.model ?: model
        if(model?.isApplyModel == true && applyModels.contains(model)) {
            model.fill(true)
            applyModels.add(model)
        }

        if(model != null) {
            if(!(tc.rule.isConditionPassed(model))) {
                throw SkipException("Skipped due to the condition restriction")
            }
            if(!tc.skipRevert) {
                stateHolder!!.revert()
            }
            uiModel = model!!.modelFromUi
            when(tc.rule) {
                is Required<*> -> testRequired(tc, model)
                is Max<*> -> testMax(tc, model)
                is Date<*> -> testRule(tc.v, tc.rule, model)
                is MoreThan<*> -> testRule(tc.v, tc.rule, model)
                is MoreOrEqThan<*> -> testRule(tc.v, tc.rule, model)
                is DependsOn<*> -> testRule(tc.v, tc.rule, model)
                is DifferanceRange<*> -> testRule(tc.v, tc.rule, model)
                is Length<*> -> testRule(tc.v, tc.rule, model)
            }
            methodCalled = true
        }
    }

    private fun testMax(vc: ValidationTc, model: IBaseModel) {
        val block = model.findSelByProp(vc.v.prop)
        vc.rule as Max

        КОГДА("значения меньшего на единицу от максимально допустимого") {
            vc.rule.input(vc.v.prop, model, (vc.rule.value - 1).toString())
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("ошибка валидации не должна отображаться") {
            block должна отсутствоватьОшибкаВалидации
            screenshot(block)
        }

        КОГДА("значение равно допустимого") {
            vc.rule.input(vc.v.prop, model, (vc.rule.value).toString())
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("ошибка валидации не должна отображаться") {
            block должна отсутствоватьОшибкаВалидации
            screenshot(block)
        }

        КОГДА("значение больше допустимого") {
            vc.rule.invalidate(vc.v.prop, uiModel)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("ошибка валидации должна отображаться c текстом '${vc.rule.hint}'") {
            block должна отображатьсяОшибкаВалидацииТекстом(vc.rule.hint)
            screenshot((block as ValidationInput).lblError)
        }

        КОГДА("корректное значение повторно введено") {
            vc.rule.revert(vc.v.prop, stateHolder!!.model)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("ошибка валидации должна исчезнуть") {
            block должна отсутствоватьОшибкаВалидации
            screenshot(block)
        }
    }

    private fun testRequired(vc: ValidationTc, model: IBaseModel) {
        val block = model.findSelByProp(vc.v.prop)
        val rule = vc.rule as Required<*>

        КОГДА("значения из обязательного удалено") {
            vc.rule.invalidate(vc.v.prop, uiModel)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("должна быть отображена ошибка валидации c текстом '${vc.rule.hint}'") {
            block должна отображатьсяОшибкаВалидацииТекстом(vc.rule.hint)
            screenshot((block as ValidationInput).lblError)
        }

        КОГДА("корректное значение введено") {
            if(!vc.v.parent.isInvalidAtStart) {
                vc.rule.revert(vc.v.prop, stateHolder!!.model)
            } else {
                model.fill(vc.v.prop as KMutableProperty<*>)
            }
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("ошибка валидации должна исчезнуть") {
            block должна отсутствоватьОшибкаВалидацииТекстом(rule.hint)
            screenshot(block)
        }
    }

    private fun testRule(v: Validation<*>, rule: Date<*>, model: IBaseModel) {
        val block = model.findSelByProp(v.prop)

        if(rule.past) {
            КОГДА("введена дата в прошлом") {
                rule.input(v.prop, model, DateHelper.dayInPast)
                (block.rootParent as Page).removeInputFocus()
            }.ТОГДА("Ошибка валидации не должна отображаться") {
                block должна отсутствоватьОшибкаВалидации
                screenshot(block)
            }
        } else {
            КОГДА("введена дата в прошлом") {
                rule.input(v.prop, model, DateHelper.dayInPast)
                (block.rootParent as Page).removeInputFocus()
            }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
                block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
                screenshot((block as ValidationInput).lblError)
            }
        }

        if(rule.current) {
            КОГДА("введена текущая дата") {
                rule.input(v.prop, model, DateHelper.currentDay)
                (block.rootParent as Page).removeInputFocus()
            }.ТОГДА("Ошибка валидации не должна отображаться") {
                block должна отсутствоватьОшибкаВалидации
                screenshot(block)
            }
        } else {
            КОГДА("введена текущая дата") {
                rule.input(v.prop, model, DateHelper.currentDay)
                (block.rootParent as Page).removeInputFocus()
            }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
                block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
                screenshot((block as ValidationInput).lblError)
            }
        }

        if(rule.future) {
            КОГДА("введена дата в будущем") {
                rule.input(v.prop, model, DateHelper.dayInFuture)
                (block.rootParent as Page).removeInputFocus()
            }.ТОГДА("Ошибка валидации не должна отображаться") {
                block должна отсутствоватьОшибкаВалидации
                screenshot(block)
            }
        } else {
            КОГДА("введена дата в будущем") {
                rule.input(v.prop, model, DateHelper.dayInFuture)
                (block.rootParent as Page).removeInputFocus()
            }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
                block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
                screenshot((block as ValidationInput).lblError)
            }
        }
    }

    private fun testRule(v: Validation<*>, rule: MoreThan<*>, model: IBaseModel) {
        val block = model.findSelByProp(v.prop)

        КОГДА("значение поля равно значению '${rule.dependsProp.name}'") {
            val originValue = rule.dependsProp.getter.call(model) as String
            rule.input(v.prop, model, originValue)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
            block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
            screenshot((block as ValidationInput).lblError)
        }

        КОГДА("значение поля меньше значения '${rule.dependsProp.name}'") {
            rule.invalidate(v.prop, uiModel)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
            block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
            screenshot((block as ValidationInput).lblError)
        }
    }

    private fun testRule(v: Validation<*>, rule: MoreOrEqThan<*>, model: IBaseModel) {
        val block = model.findSelByProp(v.prop)

        КОГДА("значение поля равно значению '${rule.dependsProp.name}'") {
            val originValue = rule.dependsProp.getter.call(model) as String
            rule.input(v.prop, model, originValue)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Ошибка валидации не должна отображаться") {
            block должна отсутствоватьОшибкаВалидации
            screenshot(block)
        }

        КОГДА("значение поля меньше чем '${rule.dependsProp.name}'") {
            rule.invalidate(v.prop, uiModel)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
            block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
            screenshot((block as ValidationInput).lblError)
        }
    }

    private fun testRule(v: Validation<*>, rule: DifferanceRange<*>, model: IBaseModel) {
        val block = model.findSelByProp(v.prop)

      *//*  КОГДА("значение поля равно значению '${rule.dependsProp.name}'") {
            val originValue = rule.dependsProp.getter.call(model) as String
            rule.input(v.prop, model, originValue)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Ошибка валидации не должна отображаться") {
            block должна отсутствоватьОшибкаВалидации
            screenshot(block)
        }*//*

        КОГДА("значение поля за пределами значения '${rule.dependsProp.name}'") {
            rule.invalidate(v.prop, uiModel)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Должна отображаться ошибка валидации c текстом '${rule.hint}'") {
            block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
            screenshot((block as ValidationInput).lblError)
        }
    }

    private fun testRule(v: Validation<*>, rule: DependsOn<*>, model: IBaseModel) {
        val items = rule.lambda()
        items.forEach {
            val (k, r) = it
            Log.step("Проверка правила валидации когда значение полня '${rule.prop.name}' равно '$k'") {
                stateHolder!!.revert()
                model.setValueByProp(rule.prop as KMutableProperty<*>, k)
                checkValidation(
                    ValidationTc(
                        v = v,
                        rule = r,
                        skipRevert = true
                    )
                )
            }
        }
    }

    private fun testRule(v: Validation<*>, rule: Length<*>, model: IBaseModel) {
        val block = model.findSelByProp(v.prop)

        КОГДА("длина значения соответствует заданным ограничениям") {
            model.fill(v.prop as KMutableProperty<*>)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Ошибка валидации не должна отображаться") {
            block должна отсутствоватьОшибкаВалидации
            screenshot(block)
        }

        КОГДА("$rule для поля") {
            rule.invalidate(v.prop, model)
            (block.rootParent as Page).removeInputFocus()
        }.ТОГДА("Ошибка валидации должна отображаться") {
            block должна отображатьсяОшибкаВалидацииТекстом(rule.hint)
            screenshot((block as ValidationInput).lblError)
        }
    }

    private fun screenshot(block: BaseSelector) {
        if(takeScreenshot) {
            val before = SeleniumBaseExecutor.enableScreenshots
            SeleniumBaseExecutor.enableScreenshots = true
            block.screenshot()
            SeleniumBaseExecutor.enableScreenshots = before
        }
    }

    private fun isBlank(vc: ValidationTc) : Boolean {
        val sel = uiModel.findSelByProp(vc.v.prop) as? ValidationDropdownSelectFirst
        if(sel != null) {
            return sel.isEmpty
        }
        return uiModel.getValueByProp(vc.v.prop).isBlank()
    }*/
}