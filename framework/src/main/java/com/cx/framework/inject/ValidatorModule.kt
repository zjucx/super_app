package com.cx.framework.inject

import com.cx.framework.validator.AdminValidator
import com.cx.framework.validator.IValidator
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ValidatorModule {
    @Binds
    @IntoMap
    @StringKey("isAdmin")
    fun bindAdminValidator(adminValidator: AdminValidator): IValidator
}