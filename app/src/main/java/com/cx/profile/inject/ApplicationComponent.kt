package com.cx.profile.inject

import com.cx.framework.event.Event
import com.cx.framework.event.RegistryInfo
import com.cx.framework.flow.IFlow
import com.cx.framework.inject.FrameworkModule
import com.cx.framework.validator.IValidator

import dagger.Component

@Component(modules = [ApplicationModule::class, FrameworkModule::class, TestModule::class])
interface ApplicationComponent {
    // This tells Dagger that LoginActivity requests injection so the graph needs to
    // satisfy all the dependencies of the fields that LoginActivity is requesting.
    fun getRegistryInfo(): Set<RegistryInfo<out Event>>
    fun getFlow(): Map<String, IFlow<*>>
}