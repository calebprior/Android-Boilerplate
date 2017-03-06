package com.calebprior.boilerplate.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.pawegio.kandroid.inflateLayout
import java.util.function.Consumer


class FlexibleAdapter<T : FlexibleAdapter.FlexibleHolder<*>> : RecyclerView.Adapter<FlexibleAdapter.FlexibleViewHolder>() {

    private val flexibleItems: MutableList<T> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = FlexibleViewHolder(parent.context.inflateLayout(viewType, parent, false))

    override fun onBindViewHolder(holder: FlexibleViewHolder, position: Int) {
        getItem(position)?.let {
            holder.handleItem(it)
        }
    }

    override fun getItemViewType(position: Int)
            = getItem(position)?.getLayout() ?: 0


    override fun getItemCount()
            = flexibleItems.size


    fun getItem(position: Int): T? {
        return if (position < flexibleItems.size) flexibleItems[position] else null
    }

    fun setItems(dataSource: List<T>) {
        if (! dataSource.isEmpty()) {
            notifyItemRangeRemoved(0, itemCount)
            flexibleItems.clear()
            addItems(dataSource)
        }
    }

    fun addItems(dataSource: List<T>) {
        if (! dataSource.isEmpty()) {
            dataSource.forEachIndexed { index, item ->
                flexibleItems.add(item)
                notifyItemInserted(index)
            }
        }
    }

    fun removeItem(item: T) {
        flexibleItems
                .withIndex()
                .filter { it.value == item }
                .forEach {
                    flexibleItems.removeAt(it.index)
                    notifyItemRemoved(it.index)
                }
    }

    fun addItem(item: T) {
        flexibleItems.add(item)
        notifyItemInserted(flexibleItems.size - 1)
    }

    fun remove(i: Int) {
        if (i in 0..(itemCount - 1)) {
            flexibleItems.removeAt(i)
            notifyItemRemoved(i)
        }
    }

    class FlexibleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun handleItem(item: FlexibleHolder<*>) {
            item.displayView(itemView)
        }

    }

    interface FlexibleHolder<T> {

        @LayoutRes fun getLayout(): Int

        fun displayView(rootView: View)

        fun setSubscription(observable: Consumer<T>)
    }
}