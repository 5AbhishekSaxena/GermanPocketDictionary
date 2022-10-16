package com.abhishek.germanPocketDictionary.utilities

import androidx.lifecycle.Observer

class Event<T>(
    private val content: T?
) {

    var isConsumed: Boolean = false
        private set

    fun getContentIfNotConsumed(): T? {
        return if (isConsumed)
            null
        else {
            isConsumed = true
            content
        }
    }
}

class EventObserver<T>(private val onChange: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotConsumed()?.let {
            onChange(it)
        }
    }
}