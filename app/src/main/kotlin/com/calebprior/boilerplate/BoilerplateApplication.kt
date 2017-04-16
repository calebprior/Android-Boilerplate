package com.calebprior.boilerplate

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.calebprior.boilerplate.di.Injector
import com.nobleworks_software.injection.android.kotlin.setAsInjector
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher


class BoilerplateApplication : MultiDexApplication() {

    companion object {
        lateinit var refWatcher: RefWatcher
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