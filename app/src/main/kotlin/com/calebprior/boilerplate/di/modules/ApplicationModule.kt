package com.calebprior.boilerplate.di.modules

import android.content.Context
import com.calebprior.boilerplate.BoilerplateApplication
import com.calebprior.boilerplate.di.scopes.ApplicationScope
import com.calebprior.boilerplate.flowcontrol.BasicFlowController
import com.calebprior.boilerplate.flowcontrol.FlowController
import dagger.Module
import dagger.Provides


@Module
@ApplicationScope
class ApplicationModule(val appContext: Context) {

    @Provides
    fun provideContext(): Context = appContext

    @Provides
    fun provideFlowController(): FlowController
            = BoilerplateApplication.get()
}