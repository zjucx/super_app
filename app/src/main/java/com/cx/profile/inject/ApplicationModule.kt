package com.cx.profile.inject

import com.cx.framework.event.*
import com.cx.framework.flow.FlowDispatcher
import com.cx.framework.trace.TraceManager
import com.cx.profile.flow.StartActivityFlowContext
import dagger.Module
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Module
class ApplicationModule {
    companion object {
        lateinit var component: ApplicationComponent
        fun init() {
            component = DaggerApplicationComponent.create()
            // init flow
            FlowDispatcher.FLOWMAP = component.getFlow()

            // test eventbus sub
            val task = {
                val registryInfos = component.getRegistryInfo()
                registryInfos.forEach {
                    EventBus.subscribeStatic(UI, AppCreateEvent::class.java, it.getEventListener())
                }
            }
            TraceManager.exec(task)

            // test eventbus publish
            GlobalScope.launch {
                var traceId = TraceManager.genTraceId()
                val event = AppCreateEvent(traceId)
                TraceManager.exec(traceId, { EventBus.publish(event) })
            }

            // test flow
            GlobalScope.launch {
                val context = StartActivityFlowContext()
                context.setFlowCode("start_activity")
                context.setRequest(true)
                TraceManager.exec{ FlowDispatcher.INSTANCE.dispatch(context) }
            }
        }
    }

}