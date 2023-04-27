package com.cx.framework.flow

import android.util.Log
import com.cx.framework.log.ProfileLog
import java.util.*

class FlowDispatcher {
    companion object {
        private val Tag = "FlowDispatcher"
        val INSTANCE = FlowDispatcher()
        var FLOWMAP = mapOf<String, IFlow<out FlowContext<*, *>>>()
    }

    fun<Req, Resp, T: FlowContext<Req, Resp>> dispatch(context: T): Optional<Resp> {
        val flowCode = context.getFlowCode()
        var flow: IFlow<out FlowContext<*, *>>? = if (FLOWMAP.containsKey(flowCode)) {
            FLOWMAP.get(flowCode)
        } else {
            return Optional.empty()
        }

        try {
            (flow as IFlow<T>).execute(context)
        } catch (ex: ClassCastException) {
            ProfileLog.it(Tag, "flowcode " + flowCode + " type cast exception")
            return Optional.empty()
        }
        return Optional.ofNullable(context.getResult())
    }
}