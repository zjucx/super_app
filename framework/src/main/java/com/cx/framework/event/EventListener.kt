package com.cx.framework.event

interface EventListener<T: Event> {
    fun onEvent(event: T)
}