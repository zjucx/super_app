package com.cx.framework.flow

import com.cx.framework.log.ProfileLog

enum class SlicePosition {
    BEFORE,
    AFTER
}

enum class SliceFlow {
    INITIATE,
    VALIDATE,
    PREPROCESS,
    PROCESS,
    AFTERPROCESS,
    EXCEPTION
}

class SliceInfo(
    var sliceFlow: SliceFlow,
    var slicePosition: SlicePosition,
    var sliceOrder: Int
    ) {
}

interface IFlowInterceptor {
    fun interception(context: FlowContext<*, *>)
}

abstract class FlowInterceptor: IFlowInterceptor {
    companion object private var TAG = "FlowInterceptor"
    override fun interception(context: FlowContext<*, *>) {
        ProfileLog.it(TAG, "flow: "+ context.sliceFlow + " slice: " + context.slicePosition)
        when (context.sliceFlow) {
            SliceFlow.INITIATE -> initiate(context)
            SliceFlow.VALIDATE -> validata(context)
            SliceFlow.PREPROCESS -> preProcess(context)
            SliceFlow.PROCESS -> process(context)
            SliceFlow.AFTERPROCESS -> aftProcess(context)
        }
    }

    abstract fun getSliceList(): List<SliceInfo>
    protected open fun beforeInitiate(context: FlowContext<*, *>) {}
    protected open fun afterInitiate(context: FlowContext<*, *>) {}
    protected open fun beforeValidata(context: FlowContext<*, *>) {}
    protected open fun afterValidata(context: FlowContext<*, *>) {}
    protected open fun beforePreProcess(context: FlowContext<*, *>) {}
    protected open fun afterPreProcess(context: FlowContext<*, *>) {}
    protected open fun beforeProcess(context: FlowContext<*, *>) {}
    protected open fun afterProcess(context: FlowContext<*, *>) {}
    protected open fun beforeAftProcess(context: FlowContext<*, *>) {}
    protected open fun afterAftProcess(context: FlowContext<*, *>) {}

    fun initiate(context: FlowContext<*, *>) {
        exec(context, { beforeInitiate(context) }, { afterInitiate(context) })
    }

    fun validata(context: FlowContext<*, *>) {
        exec(context, { beforeValidata(context) }, { afterValidata(context) })
    }

    fun preProcess(context: FlowContext<*, *>) {
        exec(context, { beforePreProcess(context) }, { afterPreProcess(context) })
    }

    fun process(context: FlowContext<*, *>) {
        exec(context, { beforeProcess(context) }, { afterProcess(context) })
    }

    fun aftProcess(context: FlowContext<*, *>) {
        exec(context, { beforeAftProcess(context) }, { afterAftProcess(context) })
    }

    private fun exec(context: FlowContext<*, *>, before: ()->Unit, after: ()->Unit) {
        when (context.slicePosition) {
            SlicePosition.BEFORE -> before()
            SlicePosition.AFTER -> after()
        }
    }
}