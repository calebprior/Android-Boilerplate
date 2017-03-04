package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.UpdateOnViewBound
import com.calebprior.boilerplate.changeAwareProperty
import com.calebprior.boilerplate.ui.presenters.HomeViewPresenter
import com.calebprior.boilerplate.ui.views.HomeView
import com.jakewharton.rxbinding.view.clicks
import com.pawegio.kandroid.find
import javax.inject.Inject

class HomeViewController(args: Bundle? = null) : BaseViewController(args), HomeView {

    @Inject
    lateinit var presenter: HomeViewPresenter

    override fun viewContent() = R.layout.view_home
    override fun presenter() = presenter

    override fun subscriptionMappings(view: View) = mapOf(
            view.find<Button>(R.id.button_increment).clicks() to { presenter.onButtonPressed() },
            view.find<Button>(R.id.button_openNewScreen).clicks() to { presenter.next() }
    )

    private var clickCount = 0

    @UpdateOnViewBound
    private var textViewText by changeAwareProperty("start", onChanged = {
        view?.find<TextView>(R.id.textView_counterText)?.text = it
    })

    override fun incrementCounter() {
        clickCount ++
        textViewText = "Clicked: $clickCount"
    }
}
