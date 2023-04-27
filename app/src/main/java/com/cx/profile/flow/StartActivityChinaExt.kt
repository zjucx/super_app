package com.cx.profile.flow

import android.util.Log
import com.cx.framework.flow.*
import com.cx.framework.log.ProfileLog
import javax.inject.Inject

// can move other module depend on you project
class StartActivityChinaExt @Inject constructor(): FlowInterceptor() {
    companion object {
        private var TAG: String = "StartActivityChinaExt"
    }
    override fun getSliceList(): List<SliceInfo> {
        return listOf(
            SliceInfo(SliceFlow.PROCESS, SlicePosition.BEFORE, 0),
            SliceInfo(SliceFlow.PROCESS, SlicePosition.AFTER, 0)
        )
    }

    override fun beforeProcess(context: FlowContext<*, *>) {
        ProfileLog.it(TAG, "beforeProcess")
    }

    override fun afterProcess(context: FlowContext<*, *>) {
        ProfileLog.it(TAG, "afterProcess")
    }
}