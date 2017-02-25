package com.calebprior.boilerplate.di

import android.content.Context
import com.calebprior.boilerplate.di.components.ApplicationComponent
import com.calebprior.boilerplate.di.components.DaggerApplicationComponent
import com.calebprior.boilerplate.di.components.GenericApplicationComponent
import com.calebprior.boilerplate.di.modules.ApplicationModule
import com.calebprior.boilerplate.di.modules.PresenterModule
import com.nobleworks_software.injection.GenericComponent
import com.nobleworks_software.injection.android.kotlin.Injector


class Injector : Injector {

    var appComponent: GenericComponent<ApplicationComponent>? = null

    fun getAppComponent(context: Context): GenericComponent<ApplicationComponent> {
        if (appComponent == null) {
            val application = context.applicationContext

            appComponent = GenericApplicationComponent(DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(application))
                    .presenterModule(PresenterModule(context))
                    .build())
        }

        return appComponent !!
    }

    override fun <T> inject(context: Context, target: T) {
        var component: GenericComponent<*>? = null

        if (component == null) {
            component = getAppComponent(context)
        }

        component.getInjector(target)?.injectMembers(target)
    }
}