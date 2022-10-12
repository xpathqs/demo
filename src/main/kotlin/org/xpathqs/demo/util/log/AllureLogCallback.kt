package org.xpathqs.demo.util.log

import org.xpathqs.demo.util.base.BaseUiTest
import io.qameta.allure.Allure
import io.qameta.allure.model.Status
import io.qameta.allure.model.StepResult
import org.xpathqs.gwt.isGiven
import org.xpathqs.gwt.isThen
import org.xpathqs.gwt.isWhen
import org.xpathqs.log.abstracts.ILogCallback
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.decorators.AttachmentMessage


annotation class ScreenshotConfig(
    /**
     * Делать скриншот перед/после лямбды Given
     */
    val beforeGiven: Boolean = false,
    val afterGiven: Boolean = false,

    /**
     * Делать скриншоты для каждого из действий внутри лямбды Given
     */
    val actionInGiven: Boolean = false,

    val beforeWhen: Boolean = false,
    val afterWhen: Boolean = false,
    val actionInWhen: Boolean = false,

    val beforeThen: Boolean = false,
    val afterThen: Boolean = false,
    val actionInThen: Boolean = false,
)

data class GWTConfigData(
    val beforeGiven: Boolean = false,
    val afterGiven: Boolean = false,
    val actionInGiven: Boolean = false,

    val beforeWhen: Boolean = false,
    val afterWhen: Boolean = false,
    val actionInWhen: Boolean = false,

    val beforeThen: Boolean = false,
    val afterThen: Boolean = false,
    val actionInThen: Boolean = false,
) {
    constructor(conf: ScreenshotConfig): this(
        beforeGiven = conf.beforeGiven,
        afterGiven = conf.afterGiven,
        actionInGiven = conf.actionInGiven,

        beforeWhen = conf.beforeWhen,
        afterWhen = conf.afterWhen,
        actionInWhen = conf.actionInWhen,

        beforeThen = conf.beforeThen,
        afterThen = conf.afterThen,
        actionInThen = conf.actionInThen
    )
}

class AllureLogCallback: ILogCallback {
    private var curUUID = ""
    val started = HashSet<String>()

    override fun onComplete(msg: IMessage, canLog: Boolean) {
        val config = config.get()
        if(started.contains(msg.bodyMessage.uuid.toString()) ) {
            if((msg.isWhen && config.afterWhen)
                || (msg.isThen && config.afterThen)
                || (msg.isGiven && config.afterGiven)
            ) {
                Allure.addAttachment("screenshot", "image/png",
                    BaseUiTest.takeScreenshot().inputStream(),
                    "png")
            }
            started.remove(msg.bodyMessage.uuid.toString())
            Allure.getLifecycle().stopStep(msg.bodyMessage.uuid.toString())
        }
    }

    override fun onLog(msg: IMessage, canLog: Boolean) {
        if(msg is AttachmentMessage) {
            Allure.addAttachment("screenshot", "image/png",
                msg.data.toByteArray().inputStream(),
                "png")
        } else if(canLog && msg.body.isNotEmpty()) {
            if(msg.bodyMessage.uuid.toString() == curUUID) {
                val result = StepResult()
                    .setName(msg.bodyMessage.toString())
                    .setStatus(Status.PASSED)

                if(msg.isThen) {
                    result.description = "expected"
                }
                started.add(curUUID);
                Allure.getLifecycle().startStep(
                    curUUID,
                    result
                )
            } else {
                Allure.step(msg.body)
            }
        }
    }

    override fun onStart(msg: IMessage) {
        curUUID = msg.bodyMessage.uuid.toString()
    }

    companion object {
        val config = ThreadLocal<GWTConfigData>()
    }
}