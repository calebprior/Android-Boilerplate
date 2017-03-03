package com.calebprior.boilerplate.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Window
import android.widget.TextView
import com.calebprior.boilerplate.R
import com.pawegio.kandroid.inflateLayout
import org.jetbrains.anko.find


class CustomProgressBar {

    var dialog: Dialog? = null
        private set

    fun stop() {
        dialog?.cancel()
        dialog = null
    }

    fun show(context: Context,
             title: CharSequence? = null,
             cancelable: Boolean = false,
             cancelListener: DialogInterface.OnCancelListener? = null
    ) {
        val view = context.inflateLayout(R.layout.progress_bar)

        title?.let {
            view.find<TextView>(R.id.id_title).text = it
        }

        dialog = Dialog(context, R.style.ProgressBarDialog)

        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(view)
            it.setCancelable(cancelable)
            it.setOnCancelListener(cancelListener)
            it.show()
        }
    }
}