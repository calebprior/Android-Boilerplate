package com.calebprior.boilerplate.di.modules

import android.content.Context
import com.calebprior.boilerplate.di.scopes.ApplicationScope
import com.calebprior.boilerplate.flowcontrol.BasicFlowController
import com.calebprior.boilerplate.flowcontrol.FlowController
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(val appContext: Context) {

    @Provides
    @ApplicationScope
    fun provideContext(): Context = appContext

    @Provides
    @ApplicationScope
    fun provideFlowController(): FlowController
            = BasicFlowController()
}