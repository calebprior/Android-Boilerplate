package com.calebprior.boilerplate.ui.viewcontrollers

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.calebprior.boilerplate.R
import com.calebprior.boilerplate.adapters.FlexibleAdapter
import com.calebprior.boilerplate.ui.base.BaseViewController
import com.calebprior.boilerplate.ui.contracts.RecyclerExampleContract
import com.jakewharton.rxbinding.view.clicks
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import android.support.v7.widget.RecyclerView as ARecyclerView


class RecyclerViewController(
        args: Bundle? = null
) : BaseViewController<RecyclerExampleContract.Presenter, RecyclerExampleContract.View>(args), RecyclerExampleContract.View {

    override fun viewContent() = R.layout.view_recycler

    override fun subscriptionMappings() = mapOf(
            find<FloatingActionButton>(R.id.floatingActionButton).clicks() to { presenter.onButtonPressed() }
    )

    val flexibleAdapter = FlexibleAdapter<FlexibleAdapter.FlexibleHolder<*>>()

    override fun onViewBound(view: View) {
        find<ARecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = flexibleAdapter
            itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f)).apply {
                addDuration = 500
            }
        }
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        presenter.loadData()
    }

    override fun addItem(item: FlexibleAdapter.FlexibleHolder<*>) {
        flexibleAdapter.addItem(item)
    }

    override fun removeItem(item: FlexibleAdapter.FlexibleHolder<*>) {
        flexibleAdapter.removeItem(item)
    }
}
