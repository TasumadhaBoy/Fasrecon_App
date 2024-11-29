package com.application.fasrecon.util

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup


fun setKeyboardMargin(root: View, view: View) {
    val rect = Rect()
    root.getWindowVisibleDisplayFrame(rect)

    val screenHeight = root.rootView.height
    val keypadHeight = screenHeight - rect.bottom

    if (keypadHeight > screenHeight * 0.4) {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = keypadHeight - view.height + 60
        view.layoutParams = layoutParams
    } else {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = 16
        view.layoutParams = layoutParams
    }
}
