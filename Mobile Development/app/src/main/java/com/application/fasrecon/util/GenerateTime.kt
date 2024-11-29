package com.application.fasrecon.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GenerateTime {
    fun generateCurrentTime(): String {
        val curr = Date()

        val timeFormat = when {
            Locale.getDefault() == Locale.UK -> SimpleDateFormat("EEE hh:mm a", Locale.UK)
            Locale.getDefault() == Locale.US -> SimpleDateFormat("EEE hh:mm a", Locale.US)
            else -> SimpleDateFormat("EEE hh:mm a", Locale("id", "ID"))
        }
        return timeFormat.format(curr)
    }
}