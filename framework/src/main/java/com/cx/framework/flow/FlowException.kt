package com.cx.framework.flow

class FlowException(var code: Int, override var message: String): RuntimeException() {
}