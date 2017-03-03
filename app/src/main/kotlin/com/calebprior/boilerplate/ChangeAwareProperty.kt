package com.calebprior.boilerplate

import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


fun <T> changeAwareProperty(initialValue: T, onChanged: (newValue: T) -> Unit): ReadWriteProperty<Any?, T>
        = object : ObservableProperty<T>(initialValue) {

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChanged(newValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val temp = super.getValue(thisRef, property)
        onChanged(temp)
        return temp
    }
}