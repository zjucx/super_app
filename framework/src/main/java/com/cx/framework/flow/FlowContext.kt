package com.cx.framework.flow

interface IFlowNotifier {
    fun complete()
}

interface IFlowContext<Req, Resp> {
    fun getFlowCode(): String

    fun getResult(): Resp

    fun getRequest(): Req

    fun getFlowNotifier(): IFlowNotifier

    fun setFlowNotifier(notifier: IFlowNotifier)
}

abstract class FlowContext<Req, Resp>: IFlowContext<Req, Resp> {
    private var flowCode: String = ""
    fun setFlowCode(value: String) {
        flowCode = value
    }
    override fun getFlowCode(): String {
        return flowCode;
    }

    private var request: Req? = null
    fun setRequest(value: Req) {
        request = value
    }
    override fun getRequest(): Req {
        return request!!
    }

    private var respound: Resp? = null
    fun setRespound(value: Resp) {
        respound = value
    }
    override fun getResult(): Resp {
        return respound!!
    }

    private lateinit var flowNotifier: IFlowNotifier
    override fun getFlowNotifier(): IFlowNotifier {
        return flowNotifier
    }

    override fun setFlowNotifier(notifier: IFlowNotifier) {
        flowNotifier = notifier
    }

    var slicePosition: SlicePosition = SlicePosition.BEFORE
        get() = field
        set(value) {
            field = value
        }

    var sliceFlow: SliceFlow = SliceFlow.INITIATE
        get() = field
        set(value) {
            field = value
        }
}