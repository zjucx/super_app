package com.cx.framework.trace

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class CoroutineTraceId(
    val id: Long
): AbstractCoroutineContextElement(CoroutineTraceId) {
    companion object Key : CoroutineContext.Key<CoroutineTraceId>
}