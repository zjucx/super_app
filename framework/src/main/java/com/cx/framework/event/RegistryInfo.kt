package com.cx.framework.event

interface RegistryInfo<T: Event> {
    fun getEventClass(): Class<T>

    fun getEventListener(): EventListener<T>
}