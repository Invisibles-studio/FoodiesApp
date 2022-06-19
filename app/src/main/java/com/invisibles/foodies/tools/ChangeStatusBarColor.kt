package com.invisibles.foodies.tools

import android.view.View
import android.view.Window
import android.view.WindowManager

class ChangeStatusBarColor(window: Window, color: Int) {

    init {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color;
        if (window.decorView.systemUiVisibility != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else
            window.decorView.systemUiVisibility = 0
    }

}