package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.CustomProgressBar
import com.calebprior.boilerplate.ui.presenters.Presenter
import com.calebprior.boilerplate.ui.views.BaseView
import com.nobleworks_software.injection.android.kotlin.inject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.inputMethodManager
import javax.inject.Inject


abstract class BaseViewController(
        args: Bundle?
) : Controller(args), BaseView, AnkoLogger {

    abstract fun viewContent(): Int
    open fun presenter(): Presenter<*> = Presenter<BaseView>(flowController)
    open fun onViewBound(view: View) {}

    @Inject
    lateinit var flowController: FlowController

    private val progressBar = CustomProgressBar()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View
            = inflater.inflate(viewContent(), container, false)

    override fun onAttach(view: View) {
        applicationContext?.inject(this)
        presenter().attachView(this)
        onViewBound(view)
    }

    override fun onDetach(view: View) {
        presenter().detachView()
    }

    override fun showLoading() {
        progressBar.show(view?.context !!, "Loading..")
    }

    override fun stopLoading() {
        progressBar.dialog?.cancel()
    }

    override fun hideKeyboard() {
        var view = activity?.currentFocus

        if (view == null) {
            view = View(activity)
        }

        activity?.inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showSnackBar(
            message: String,
            id: Int,
            length: Int,
            actionText: String,
            action: () -> Unit
    ) {
        val snackBar = Snackbar.make(activity !!.find(id), message, length)
        snackBar.setAction(actionText, { action() })
        snackBar.show()
    }
}