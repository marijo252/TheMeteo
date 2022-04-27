package com.example.android.themeteo.utils

import android.widget.TextView
import com.example.android.themeteo.R

fun bindTextViewToTemperatureFormat(textView: TextView, number: Int) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.temperature), number)
}