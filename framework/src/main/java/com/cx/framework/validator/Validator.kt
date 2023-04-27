package com.cx.framework.validator

import com.cx.framework.log.ProfileLog
import com.cx.framework.validator.IValidator.Companion.IS_ADMIN
import javax.inject.Inject

interface IValidator {
    companion object {
        var IS_ADMIN = "isAdmin"
        var PARAM_NULL = "paramNull"
    }
    fun<T> validate(value: T, message: String)
}

class Validator @Inject constructor(private var validatorMap: Map<String, @JvmSuppressWildcards IValidator>) {
    companion object {
        val TAG = "Validator"
    }

    fun<T> checkAdmin(value: T, message: String): Validator {
        return check(IS_ADMIN, value, message)
    }

    fun<T> check(validatorName: String, value: T, message: String): Validator {
        ProfileLog.it(TAG, "check " + validatorMap.get(validatorName).toString())
        validatorMap.get(validatorName)?.validate(value, message)
        return this;
    }
}