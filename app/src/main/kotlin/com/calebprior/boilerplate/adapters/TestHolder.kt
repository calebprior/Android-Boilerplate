package com.calebprior.boilerplate.adapters

import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.data.TestModel
import com.pawegio.kandroid.find
import org.jetbrains.anko.onClick
import java.util.function.Consumer


class TestHolder(
        val test: TestModel
) : FlexibleAdapter.FlexibleHolder<TestHolder> {

    lateinit var clickListener: Consumer<TestHolder>

    override fun setSubscription(observable: Consumer<TestHolder>) {
        clickListener = observable
    }

    override fun displayView(rootView: View) {
        rootView.find<TextView>(R.id.item_text).text = test.text

        rootView.find<CardView>(R.id.card_view).onClick {
            clickListener.accept(this)
        }
    }

    override fun getLayout() = R.layout.item_test
}