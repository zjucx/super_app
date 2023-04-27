package com.cx.framework.inject

import com.cx.framework.event.AppCreateEventRegistryInfo
import com.cx.framework.event.Event
import com.cx.framework.event.RegistryInfo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface ListenOnModule {
    @Binds
    @IntoSet
    fun bindAppCreateEvent(info: AppCreateEventRegistryInfo): RegistryInfo<out Event>
}