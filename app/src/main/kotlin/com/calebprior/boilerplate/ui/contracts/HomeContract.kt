package com.calebprior.boilerplate.ui.contracts

import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.base.BasePresenter
import com.calebprior.boilerplate.ui.base.BaseView
import com.pawegio.kandroid.runDelayed


class HomeContract {

    class Presenter(
            flowController: FlowController
    ) : BasePresenter<View>(flowController) {

        fun onButtonPressed() {
            //simulate loading
            view?.showLoading()

            runDelayed(1000) {
                stopLoading()
                view?.incrementCounter()
            }
        }

        fun next() {
            flowController.goToTest()
        }
    }

    interface View : BaseView {

        fun incrementCounter()

    }
}