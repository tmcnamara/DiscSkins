package com.seadog.discskins.util

import android.content.Context
import android.content.Intent
import android.content.Intent.*
import androidx.core.net.toUri


class EmailUtil {

    fun sendEmail(context: Context){
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
            .setData("mailto:seadogbenicia@gmail.com".toUri())
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(EXTRA_EMAIL, arrayOf("seadogsoftware@gmail.com"))
            putExtra(EXTRA_SUBJECT, "DiscSkins Feedback")
            putExtra(EXTRA_TEXT, "Feel free to provide us feedback.")
            selector = selectorIntent
        }
        context.startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
    }
}