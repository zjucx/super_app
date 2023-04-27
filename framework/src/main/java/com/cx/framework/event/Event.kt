package com.cx.framework.event

import com.cx.framework.trace.Trace

abstract class Event: Trace() {
    abstract fun getName(): String
}