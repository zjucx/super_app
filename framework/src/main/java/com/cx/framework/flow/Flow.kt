package com.cx.framework.flow

import android.util.Log
import com.cx.framework.log.ProfileLog

interface IFlow<T: FlowContext<*, *>> {
    fun execute(context: T)
}

interface IFlowLock {
    fun lock()
    fun unlock()
}

interface IBaseFlow<T: FlowContext<*, *>> {
    fun initiate(context: T)
    fun validata(context: T)
    fun preProcess(context: T)
    fun process(context: T)
    fun afterProcess(context: T)
    fun getFlowLock(): IFlowLock
    fun exceptionProcess(flowContext: T, curSliceFlow: SliceFlow, ex: FlowException)
}

abstract class Flow<T: FlowContext<*, *>>: IFlow<T>, IFlowNotifier {
    companion object {
        private var TAG: String = "Flow"
    }
    private lateinit var flowContext: T
    private var complete = false
    private lateinit var curSliceFlow: SliceFlow

    abstract fun getBaseFlow(): IBaseFlow<T>
    abstract fun getFlowInterceptor(): Set<FlowInterceptor>

    override fun execute(context: T) {
        try {
            flowContext = context
            flowContext.setFlowNotifier(this)
            initiate()
            try {
//                getBaseFlow().getFlowLock().lock()
                validata()
                preProcess()
                process()
                afterProcess()
            } finally {
//                getBaseFlow().getFlowLock().unlock()
            }
        } catch (ex: FlowException) {
            exceptionProcess(ex)
            ProfileLog.it(TAG, "exception code: " + ex.code + " message: " + ex.message)
        }
    }

    override fun complete() {
        complete = true
    }

    private fun execInterception(sliceFlow: SliceFlow, slicePosition: SlicePosition) {
        if(complete) return

        flowContext.sliceFlow = sliceFlow
        flowContext.slicePosition = slicePosition
        getFlowInterceptor().filter {
            !it.getSliceList().filter {
                it.sliceFlow == sliceFlow && it.slicePosition == slicePosition
            }.isEmpty()
        }.forEach { it.interception(flowContext) }
    }

    fun initiate() {
        curSliceFlow = SliceFlow.INITIATE
        execInterception(SliceFlow.INITIATE, SlicePosition.BEFORE)
        if (!complete)
            getBaseFlow().initiate(flowContext)
        execInterception(SliceFlow.INITIATE, SlicePosition.AFTER)
    }

    fun validata() {
        curSliceFlow = SliceFlow.VALIDATE
        execInterception(SliceFlow.VALIDATE, SlicePosition.BEFORE)
        if (!complete)
            getBaseFlow().validata(flowContext)
        execInterception(SliceFlow.VALIDATE, SlicePosition.AFTER)
    }

    fun preProcess() {
        curSliceFlow = SliceFlow.PREPROCESS
        execInterception(SliceFlow.PREPROCESS, SlicePosition.BEFORE)
        if (!complete)
            getBaseFlow().preProcess(flowContext)
        execInterception(SliceFlow.PREPROCESS, SlicePosition.AFTER)
    }

    fun process() {
        curSliceFlow = SliceFlow.PROCESS
        execInterception(SliceFlow.PROCESS, SlicePosition.BEFORE)
        if (!complete)
            getBaseFlow().process(flowContext)
        execInterception(SliceFlow.PROCESS, SlicePosition.AFTER)
    }

    fun afterProcess() {
        curSliceFlow = SliceFlow.AFTERPROCESS
        execInterception(SliceFlow.AFTERPROCESS, SlicePosition.BEFORE)
        if (!complete)
            getBaseFlow().afterProcess(flowContext)
        execInterception(SliceFlow.AFTERPROCESS, SlicePosition.AFTER)
    }

    fun exceptionProcess(ex: FlowException) {
        curSliceFlow = SliceFlow.EXCEPTION
        execInterception(SliceFlow.EXCEPTION, SlicePosition.BEFORE)
        if (!complete)
            getBaseFlow().exceptionProcess(flowContext, curSliceFlow, ex)
        execInterception(SliceFlow.EXCEPTION, SlicePosition.AFTER)
    }
}