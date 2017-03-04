package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.UpdateOnViewBound
import com.calebprior.boilerplate.changeAwareProperty
import com.calebprior.boilerplate.ui.presenters.HomeViewPresenter
import com.calebprior.boilerplate.ui.views.HomeView
import com.jakewharton.rxbinding.view.clicks

class HomeViewController(
        args: Bundle? = null
) : BaseViewController<HomeViewPresenter, HomeView>(args), HomeView {

    override fun viewContent() = R.layout.view_home

    override fun subscriptionMappings() = mapOf(
            find<Button>(R.id.button_increment).clicks() to { presenter.onButtonPressed() },
            find<Button>(R.id.button_openNewScreen).clicks() to { presenter.next() }
    )

    private var clickCount = 0

    @UpdateOnViewBound
    private var textViewText by changeAwareProperty("start", onChanged = {
        find<TextView>(R.id.textView_counterText).text = it
    })

    override fun incrementCounter() {
        clickCount ++
        textViewText = "Clicked: $clickCount"
    }
}