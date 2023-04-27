package com.cx.framework.inject

import com.cx.framework.flow.FlowInterceptor
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import javax.inject.Named

@Module(includes = [ListenOnModule::class, ValidatorModule::class])
interface FrameworkModule {
    @Multibinds
    @Named("start_activity")
    fun bindStartActivitySet(): Set<FlowInterceptor>
}