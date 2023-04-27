package com.cx.framework.event

import com.cx.framework.log.ProfileLog
import com.cx.framework.trace.TraceManager
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

val UI: CoroutineDispatcher = Dispatchers.Main

object EventBus: CoroutineScope {
    private val TAG = "EventBus"

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Default + job
    private val eventBusMap = ConcurrentHashMap<String, MutableMap<Class<*>, BusListener<*>>>()

    @JvmStatic
    fun subscribeStatic(eventDispatcher: CoroutineDispatcher = UI,
                        eventClass: Class<out Event>, listener: EventListener<out Event>){
        ProfileLog.it(TAG, "subscribeStatic " + eventClass.simpleName + " " + listener::class.simpleName)
        val busListenerMap = if (eventBusMap.containsKey(eventClass.simpleName)) {
            eventBusMap[eventClass.simpleName]!!
        } else {
            val busListenerMap = mutableMapOf<Class<*>, BusListener<*>>()
            eventBusMap[eventClass.simpleName] = busListenerMap
            busListenerMap
        }
        busListenerMap[listener::class.java] =
            BusListener(this, eventDispatcher, listener)
    }

    @JvmStatic
    fun <T: Event>publish(event: T) {
        ProfileLog.it(TAG, "publish " + event.javaClass)
        val listenerMap = eventBusMap[event::class.simpleName]
        if (listenerMap != null) {
            listenerMap.forEach { _, busListener -> busListener.publish(event) }
        }
    }

    data class BusListener <T: Event> (
        val coroutineScope: CoroutineScope,
        val eventDispatcher: CoroutineDispatcher,
        val listener: EventListener<T>) {
        private var channel = Channel<T>()

        init {
            coroutineScope.launch {
                channel.consumeEach {
                    launch(eventDispatcher + TraceManager.resetCorTraceId(it.traceid)) {
                        ProfileLog.it(TAG, "BusListener consumeEach eventDispatcher " + it::class.simpleName)
                        listener.onEvent(it)
                    }
                }
            }
        }

        fun publish(event: Any) {
            if (!channel.isClosedForSend) {
                ProfileLog.it(TAG, "BusListener publish event " + event::class.simpleName)
                coroutineScope.launch {
                    channel.send(event as T)
                }
            } else {
                ProfileLog.it(TAG, "Channel is closed for send")
            }
        }

        fun cancel() {
            channel.cancel()
        }
    }
}