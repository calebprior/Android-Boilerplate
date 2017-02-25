package com.calebprior.boilerplate.ui.views

import android.support.design.widget.Snackbar
import com.calebprior.boilerplate.R


interface BaseView {

    fun showLoading()
    fun stopLoading()

    fun hideKeyboard()

    fun showSnackBar(message: String,
                     id: Int = R.id.root_view,
                     length: Int = Snackbar.LENGTH_SHORT,
                     actionText: String = "",
                     action: () -> Unit = {})
}