package com.calebprior.boilerplate.di.modules

import android.content.Context
import com.calebprior.boilerplate.di.scopes.ApplicationScope
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.presenters.HomeViewPresenter
import dagger.Module
import dagger.Provides


@Module
@ApplicationScope
class PresenterModule(val context: Context) {

    @Provides
    fun provideHomeViewPresenter(
            flowController: FlowController
    ): HomeViewPresenter
            = HomeViewPresenter(flowController)

}