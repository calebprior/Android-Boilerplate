package com.calebprior.boilerplate.flowcontrol

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.calebprior.boilerplate.ui.viewcontrollers.HomeViewController
import org.jetbrains.anko.AnkoLogger


class BasicFlowController(

) : FlowController, AnkoLogger {

    override fun goToTest() {
        router.pushController(RouterTransaction.with(HomeViewController()).popChangeHandler(FadeChangeHandler()).pushChangeHandler(FadeChangeHandler()))
    }

    lateinit var router: Router

    override fun attachRouter(router: Router) {
        this.router = router
    }

    override fun launchHomeScreen() {
        if (! router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HomeViewController()))
        }
    }


    override fun onBackPressed() = router.handleBack()

}