package com.calebprior.boilerplate

import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


fun <T> changeAwareProperty(initialValue: T, onChange: (newValue: T) -> Unit): ReadWriteProperty<Any?, T>
        = object : ObservableProperty<T>(initialValue) {

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(newValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val temp = super.getValue(thisRef, property)
        onChange(temp)
        return temp
    }
}