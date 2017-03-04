package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.calebprior.boilerplate.BoilerplateApplication
import com.calebprior.boilerplate.UpdateOnViewBound
import com.calebprior.boilerplate.flowcontrol.FlowController
import com.calebprior.boilerplate.ui.CustomProgressBar
import com.calebprior.boilerplate.ui.presenters.Presenter
import com.calebprior.boilerplate.ui.views.BaseView
import com.nobleworks_software.injection.android.kotlin.inject
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.inputMethodManager
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible


abstract class BaseViewController(
        args: Bundle?
) : Controller(args), BaseView, AnkoLogger {

    abstract fun viewContent(): Int
    open fun presenter(): Presenter<*> = Presenter<BaseView>(flowController)
    open fun onViewBound(view: View) {}

    private var hasExited: Boolean = false

    @Inject
    lateinit var flowController: FlowController

    private val progressBar = CustomProgressBar()

    val subs = CompositeSubscription()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View
            = inflater.inflate(viewContent(), container, false)

    override fun onAttach(view: View) {
        applicationContext?.inject(this)
        presenter().attachView(this)

        // Call the getter for each property marked with UpdateOnViewBound, to force onChange to run
        this.javaClass.kotlin.declaredMemberProperties
                .filterIsInstance<KProperty<*>>()
                .filter { it.annotations.any { it.annotationClass == UpdateOnViewBound::class } }
                .forEach {
                    it.isAccessible = true
                    it.getter.call(this)
                }

        onViewBound(view)
    }

    override fun onDetach(view: View) {
        presenter().detachView()
        stopLoading()
        subs.clear()
    }

    override fun showLoading() {
        progressBar.show(view?.context !!, "Loading..")
    }

    override fun stopLoading() {
        progressBar.stop()
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

    override fun onDestroy() {
        super.onDestroy()
        stopLoading()

        if (hasExited) {
            BoilerplateApplication.refWatcher.watch(this)
        }
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)

        hasExited = ! changeType.isEnter
        if (isDestroyed) {
            BoilerplateApplication.refWatcher.watch(this)
        }
    }
}