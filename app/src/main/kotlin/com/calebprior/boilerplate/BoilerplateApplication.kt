package com.calebprior.boilerplate

import android.app.Application
import com.calebprior.boilerplate.di.Injector
import com.calebprior.boilerplate.flowcontrol.BasicFlowController
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.nobleworks_software.injection.android.kotlin.setAsInjector


class BoilerplateApplication : Application() {

    companion object {
        var flowController: FlowController? = null

        fun get(): FlowController {
            if (flowController == null) {
                flowController = BasicFlowController()
            }

            return flowController !!
        }
    }

    override fun onCreate() {
        super.onCreate()
        Injector().setAsInjector()
    }
}