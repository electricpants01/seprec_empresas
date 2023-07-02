package com.locotodevteam.seprecempresas.view

import android.view.View


var View.setVisibility : Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }