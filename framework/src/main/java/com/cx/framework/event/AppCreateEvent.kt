package com.cx.framework.event

import com.cx.framework.log.ProfileLog
import com.cx.framework.validator.Validator
import javax.inject.Inject

class AppCreateEvent(override var traceid: String) : Event() {
    override fun getName(): String {
        return "AppCreateEvent"
    }
}

class AppCreateListener @Inject constructor(): EventListener<AppCreateEvent> {
    companion object private var Tag : String = "AppCreateListener";
    @Inject lateinit var validator: Validator
    override fun onEvent(event: AppCreateEvent) {
        validator.checkAdmin("", "admin")
        ProfileLog.it(Tag, "onEvent")
    }
}

class AppCreateEventRegistryInfo @Inject constructor(): RegistryInfo<AppCreateEvent>{
    companion object private var Tag : String = "AppCreateEventRegistryInfo"
    @Inject lateinit var listener: AppCreateListener

    override fun getEventClass(): Class<AppCreateEvent> {
        ProfileLog.it(Tag, "getEventClass")
        return AppCreateEvent::class.javaObjectType
    }

    override fun getEventListener(): EventListener<AppCreateEvent> {
        ProfileLog.it(Tag, "getEventListener")
        return listener
    }
}