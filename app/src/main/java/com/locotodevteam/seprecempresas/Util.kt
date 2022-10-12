package com.locotodevteam.seprecempresas

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.toDate() : Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.getDefault())
    return format.parse(this)
}

fun Date.formatDate() : String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(this)
}

class Util {
}