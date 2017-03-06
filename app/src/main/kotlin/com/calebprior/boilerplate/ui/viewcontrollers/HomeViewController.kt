package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.ui.base.BaseViewController
import com.calebprior.boilerplate.ui.contracts.HomeContract
import com.calebprior.boilerplate.utility.UpdateOnViewBound
import com.calebprior.boilerplate.utility.changeAwareProperty
import com.jakewharton.rxbinding.view.clicks

class HomeViewController(
        args: Bundle? = null
) : BaseViewController<HomeContract.Presenter, HomeContract.View>(args), HomeContract.View {

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