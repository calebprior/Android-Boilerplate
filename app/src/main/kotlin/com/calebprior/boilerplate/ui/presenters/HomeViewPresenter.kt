package com.calebprior.boilerplate.ui.presenters

import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.views.HomeView
import com.pawegio.kandroid.runDelayed


class HomeViewPresenter(
        flowController: FlowController
) : Presenter<HomeView>(flowController) {

    fun onButtonPressed() {
        //simulate loading
        view?.showLoading()

        runDelayed(1000) {
            view?.stopLoading()
            view?.setText("Finished")
        }
    }

}