package com.skilrock.retailapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.security.MessageDigest

fun Context.redToast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    val view = toast.view

    view?.background?.setColorFilter(Color.parseColor("#d30e24"), PorterDuff.Mode.SRC_IN)

    val text = view?.findViewById<TextView>(android.R.id.message)
    text?.setTextColor(Color.WHITE)
    toast.show()
}

fun Context.greenToast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    val view = toast.view

    view?.background?.setColorFilter(Color.parseColor("#089148"), PorterDuff.Mode.SRC_IN)

    val text = view?.findViewById<TextView>(android.R.id.message)
    text?.setTextColor(Color.WHITE)
    toast.show()
}

fun md5Convert(s: String): String {
    val technique = "MD5"
    try {
        // Create MD5 Hash
        val digest = MessageDigest
            .getInstance(technique)
        digest.update(s.toByteArray())
        val messageDigest = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: Exception) {
        Log.d("TAg", "md5Convert: $e")
        e.printStackTrace()
    }
    return ""
}
