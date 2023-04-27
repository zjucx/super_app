package com.cx.profile.inject

import com.cx.framework.flow.FlowInterceptor
import com.cx.framework.flow.IFlow
import com.cx.profile.flow.StartActivityChinaExt
import com.cx.profile.flow.StartActivityFlow
import com.cx.profile.flow.StartActivityOverseaExt
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import javax.inject.Inject
import javax.inject.Named

@Module
interface TestModule {
    @Binds
    @IntoMap
    @StringKey("start_activity")
    fun bindStartActivityFlow(startActivityFlow: StartActivityFlow): IFlow<*>

    @Binds
    @IntoSet
    @Named("start_activity")
    fun bindStartActivityChinaExt(startActivityChinaExt: StartActivityChinaExt): FlowInterceptor

    @Binds
    @IntoSet
    @Named("start_activity")
    fun bindStartActivityOverseaExt(startActivityOverseaExt: StartActivityOverseaExt): FlowInterceptor
}