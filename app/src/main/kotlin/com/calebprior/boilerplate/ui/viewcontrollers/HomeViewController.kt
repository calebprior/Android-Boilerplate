package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.calebprior.boilerplate.R
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

    override fun onViewBound(view: View) {
        view.find<Button>(R.id.button).clicks().subscribe {
            presenter.onButtonPressed()
        }
    }

    override fun setText(newText: String) {
        view?.find<TextView>(R.id.textView)?.text = newText
    }
}