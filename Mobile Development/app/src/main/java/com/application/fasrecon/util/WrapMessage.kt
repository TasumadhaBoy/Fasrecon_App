package com.application.fasrecon.util

open class WrapMessage<out T>(private val data: T) {
    private var hasDisplayed = false

    fun getDataIfNotDisplayed(): T? {
        return if (hasDisplayed) {
            null
        } else {
            hasDisplayed = true
            data
        }
    }
}