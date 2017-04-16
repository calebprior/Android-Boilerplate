package com.calebprior.boilerplate.di.components

import com.calebprior.boilerplate.BoilerplateApplication
import com.calebprior.boilerplate.MainActivity
import com.calebprior.boilerplate.di.modules.ApplicationModule
import com.calebprior.boilerplate.di.modules.PresenterModule
import com.calebprior.boilerplate.di.scopes.ApplicationScope
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.viewcontrollers.HomeViewController
import com.calebprior.boilerplate.ui.viewcontrollers.RecyclerViewController
import com.calebprior.boilerplate.ui.viewcontrollers.TestViewController
import dagger.Component


@Component(
        modules = arrayOf(
                ApplicationModule::class,
                PresenterModule::class
        )
)
@ApplicationScope
interface ApplicationComponent {

    fun flowController(): FlowController

    // Application Level
    fun inject(application: BoilerplateApplication)

    fun inject(mainActivity: MainActivity)

    // Injection targets
    fun inject(homeViewController: HomeViewController)

    fun inject(recyclerViewController: RecyclerViewController)

    fun inject(testViewController: TestViewController)
}