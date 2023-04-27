package com.cx.profile.flow

import com.cx.framework.flow.*
import com.cx.framework.log.ProfileLog
import javax.inject.Inject

// can move other module depend on you project
class StartActivityOverseaExt @Inject constructor(): FlowInterceptor() {
    companion object {
        private var TAG: String = "StartActivityOverseaExt"
    }
    override fun getSliceList(): List<SliceInfo> {
        return listOf(
            SliceInfo(SliceFlow.AFTERPROCESS, SlicePosition.BEFORE, 0),
            SliceInfo(SliceFlow.PROCESS, SlicePosition.AFTER, 0)
        )
    }

    override fun beforeAftProcess(context: FlowContext<*, *>) {
        ProfileLog.it(TAG, "beforeAftProcess")
    }

    override fun afterProcess(context: FlowContext<*, *>) {
        ProfileLog.it(TAG, "afterProcess")
    }
}