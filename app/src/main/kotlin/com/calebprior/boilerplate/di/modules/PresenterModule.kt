package com.calebprior.boilerplate.di.modules

import android.content.Context
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.base.BasePresenter
import com.calebprior.boilerplate.ui.base.BaseView
import com.calebprior.boilerplate.ui.contracts.HomeContract
import com.calebprior.boilerplate.ui.contracts.RecyclerExampleContract
import dagger.Module
import dagger.Provides


@Module
class PresenterModule(val context: Context) {

    @Provides
    fun provideHomeViewPresenter(
            flowController: FlowController
    ): HomeContract.Presenter
            = HomeContract.Presenter(flowController)

    @Provides
    fun provideRecyclerPresenter(
            flowController: FlowController
    ): RecyclerExampleContract.Presenter
            = RecyclerExampleContract.Presenter(flowController)

    @Provides
    fun provideBasePresenter(
            flowController: FlowController
    ): BasePresenter<BaseView>
            = BasePresenter(flowController)

}