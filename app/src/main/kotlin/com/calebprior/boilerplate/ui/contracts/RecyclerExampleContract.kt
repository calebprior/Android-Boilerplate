package com.calebprior.boilerplate.ui.contracts

import com.calebprior.boilerplate.adapters.FlexibleAdapter
import com.calebprior.boilerplate.adapters.TestHolder
import com.calebprior.boilerplate.data.TestModel
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.base.BasePresenter
import com.calebprior.boilerplate.ui.base.BaseView
import java.util.function.Consumer


class RecyclerExampleContract {

    class Presenter(
            flowController: FlowController
    ) : BasePresenter<View>(flowController) {

        fun loadData() {
            for (i in 1..5) {
                view?.addItem(TestHolder(TestModel("item $i")).apply { setSubscription(clickListener()) })
            }
        }

        private fun clickListener() = Consumer<TestHolder> {
            view?.removeItem(it)
        }

        fun onButtonPressed() {
            view?.addItem(TestHolder(TestModel("added item")).apply { setSubscription(clickListener()) })
        }
    }

    interface View : BaseView {
        fun addItem(item: FlexibleAdapter.FlexibleHolder<*>)
        fun removeItem(item: FlexibleAdapter.FlexibleHolder<*>)
    }
}