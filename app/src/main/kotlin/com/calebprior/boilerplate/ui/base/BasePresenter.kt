package com.calebprior.boilerplate.ui.base

import android.support.design.widget.Snackbar
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.base.BaseView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class BasePresenter<V : BaseView>(
        var flowController: FlowController
) : AnkoLogger {

    var view: V? = null

    open fun afterAttach() {}
    open fun afterDetach() {}
    open fun afterError() {}

    fun attachView(view: V) {
        this.view = view
        afterAttach()
    }

    fun detachView() {
        view = null
        afterDetach()
    }

    fun handleError(e: Throwable, message: String = "Error Occurred") {
        info(e)

        hideKeyboard()
        stopLoading()
        showSnackBar(message)

        afterError()
    }

    fun hideKeyboard() {
        view?.hideKeyboard()
    }

    fun showLoading() {
        view?.showLoading()
    }

    fun stopLoading() {
        view?.stopLoading()
    }

    fun showSnackBar(message: String,
                     id: Int = R.id.root_view,
                     length: Int = Snackbar.LENGTH_SHORT,
                     actionText: String = "",
                     action: () -> Unit = {}
    ) {
        view?.showSnackBar(message, id, length, actionText, action)
    }
}