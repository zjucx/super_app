package com.cx.framework.validator

import com.cx.framework.log.ProfileLog
import javax.inject.Inject

class AdminValidator @Inject constructor(): IValidator {
    companion object {
        var TAG = "AdminValidator"
    }
    override fun <Void> validate(value: Void, message: String) {
        // if validate failed throw exception
        ProfileLog.it(TAG, "validate")
    }
}