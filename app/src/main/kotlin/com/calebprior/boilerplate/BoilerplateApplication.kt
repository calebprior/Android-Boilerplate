package com.calebprior.boilerplate

import android.app.Application
import com.calebprior.boilerplate.di.Injector
import com.calebprior.boilerplate.flowcontrol.BasicFlowController
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.nobleworks_software.injection.android.kotlin.setAsInjector
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher


class BoilerplateApplication : Application() {

    companion object {
        var flowController: FlowController? = null

        lateinit var refWatcher: RefWatcher

        fun get(): FlowController {
            if (flowController == null) {
                flowController = BasicFlowController()
            }

            return flowController !!
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        refWatcher = LeakCanary.install(this);

        Injector().setAsInjector()
    }
}