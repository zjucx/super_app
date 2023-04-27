package com.cx.profile.flow

import com.cx.framework.flow.*
import com.cx.framework.log.ProfileLog
import javax.inject.Inject
import javax.inject.Named

class StartActivityFlow @Inject constructor(): Flow<StartActivityFlowContext>(),
    IBaseFlow<StartActivityFlowContext> {
    companion object {
        private val TAG = "StartActivityFlow"
    }

    @Inject
    @Named("start_activity")
    lateinit var startAbilityInterceptor: Set<@JvmSuppressWildcards FlowInterceptor>

    override fun getBaseFlow(): IBaseFlow<StartActivityFlowContext> {
        return this
    }

    override fun initiate(context: StartActivityFlowContext) {
        ProfileLog.it(TAG, "initiate")
    }

    override fun validata(context: StartActivityFlowContext) {
        ProfileLog.it(TAG, "validata")
    }

    override fun preProcess(context: StartActivityFlowContext) {
        ProfileLog.it(TAG, "preProcess")
    }

    override fun process(context: StartActivityFlowContext) {
        ProfileLog.it(TAG, "process")
    }

    override fun afterProcess(context: StartActivityFlowContext) {
        context.setRespound(context.getRequest())
        ProfileLog.it(TAG, "afterProcess " + context.getRequest())
    }

    override fun getFlowLock(): IFlowLock {
        return TODO()
    }

    override fun getFlowInterceptor(): Set<FlowInterceptor> {
        return startAbilityInterceptor
    }

    override fun exceptionProcess(
        flowContext: StartActivityFlowContext,
        curSliceFlow: SliceFlow,
        ex: FlowException
    ) {
        flowContext.setRespound(false)
    }
}