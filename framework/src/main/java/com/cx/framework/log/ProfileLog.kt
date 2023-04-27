package com.cx.framework.log

import android.util.Log
import com.cx.framework.trace.TraceManager

class ProfileLog {
    companion object {
        @JvmStatic
        fun it(tag: String, msg: String) {
            var traceid = if (TraceManager.getTraceId() == null)
                ""
            else
                "[" + TraceManager.getTraceId() + "]"
            Log.i(traceid + tag, msg)
        }
    }
}