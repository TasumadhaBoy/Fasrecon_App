package com.application.fasrecon.util

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResourceUtil {
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    IdlingResourceUtil.increment()
    return try {
        function()
    } finally {
        IdlingResourceUtil.decrement()
    }
}