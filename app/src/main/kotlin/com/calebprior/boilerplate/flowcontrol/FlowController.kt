package com.calebprior.boilerplate.flowcontrol

import com.bluelinelabs.conductor.Router


interface FlowController {

    fun launchHomeScreen()

    fun onBackPressed(): Boolean

    fun attachRouter(router: Router)
}