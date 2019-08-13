package com.sagar.android.onlynetwork.util

class Event<T> {
    private var content: T? = null
    private var shouldReadOnlyOnce = true
    private var dataHandled = false

    constructor(content: T) {
        this.content = content
    }

    constructor(content: T, shouldReadOnlyOnce: Boolean) {
        this.content = content
        this.shouldReadOnlyOnce = shouldReadOnlyOnce
    }

    fun getContent(): T? {
        if (!shouldReadOnlyOnce)
            return content
        if (!dataHandled) {
            dataHandled = true
            return content
        }
        return null
    }

    fun shouldReadContent(): Boolean {
        if (!shouldReadOnlyOnce)
            return true

        return shouldReadOnlyOnce && !dataHandled
    }

    fun readContent() {
        dataHandled = true
    }
}
