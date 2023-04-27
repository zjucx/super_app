package com.cx.framework.trace

import kotlinx.coroutines.ThreadContextElement
import kotlinx.coroutines.asContextElement
import java.lang.NullPointerException
import java.util.*

class TraceManager {
    companion object {
        var count: Int = 0
        var threadLocal: InheritableThreadLocal<String> = InheritableThreadLocal<String>()

        @JvmStatic
        public final fun genTraceId(): String {
            var seq = String.format(Locale.ENGLISH, "%02d", ++count)
            var traceId = String.format(Locale.ENGLISH, "%07X", System.currentTimeMillis()) + seq
            threadLocal.set(traceId)
            return traceId
        }

        @JvmStatic
        public final fun getTraceId(): String {
            return threadLocal.get()
        }

        @JvmStatic
        public final fun resetCorTraceId(value: String): ThreadContextElement<String> {
            return threadLocal.asContextElement(value)
        }

        @JvmStatic
        public final fun resetTraceId(value: String) {
            return threadLocal.set(value)
        }

        @JvmStatic
        public final fun clearTraceId() {
            return threadLocal.remove()
        }

        @JvmStatic
        public final fun exec(run: () -> Unit) {
            try {
                genTraceId()
                run()
            } finally {
                clearTraceId()
            }
        }

        @JvmStatic
        public final fun exec(traceId:String, run: () -> Unit) {
            try {
                if (traceId == null)
                    genTraceId()
                run()
            } finally {
                clearTraceId()
            }
        }
    }
}