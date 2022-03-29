package com.seadog.discskins.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.graphics.applyCanvas
import java.io.File


class FileShareUtil() {

    fun shareFile(view: View?, context: Context) {
        view?.let {
            val bmp = Bitmap.createBitmap(
                view.width, view.height,
                Bitmap.Config.ARGB_8888
            ).applyCanvas {
                view.draw(this)
            }
            bmp.let {

                Log.i("TIMBO", "filesDir - context.filesDir")
                val externalPath = File(context.getExternalFilesDir(null), "external_files")
                if (!externalPath.exists()) {
                    externalPath.mkdir()
                }
                Log.i("TIMBO", "created ${externalPath.absolutePath}")
                val file = File(externalPath, "screenshot.png")
                if (!file.exists()) {
                    file.createNewFile()
                }
                Log.i("TIMBO", "created ${file.absolutePath}")
                file.writeBitmap(bmp, Bitmap.CompressFormat.PNG, 85).also {
                    sendIntent(file, context)
                }
            }
        }
    }

    private fun sendIntent(file: File, context: Context) {
        if (file.exists()) {
            try {
                val uri = FileProvider.getUriForFile(
                    context, context.applicationContext
                        .packageName.toString() + ".provider", file
                )

                val intentShareFile = Intent(Intent.ACTION_SEND)
                intentShareFile.type = "application/pdf"
                intentShareFile.putExtra(Intent.EXTRA_STREAM, uri)
                intentShareFile.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Sharing File from DiscSkins..."
                )
                intentShareFile.putExtra(
                    Intent.EXTRA_TEXT,
                    "Sharing File from DiscSkins..."
                )
                context.startActivity(Intent.createChooser(intentShareFile, "Share File DiscSkins"))

            } catch (e: Exception) {
                Log.e("TIMBO", "yuck - $e")
            }
        } else {
            Log.i("TIMBO", "File doesn't exist")
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    fun showHelpVideo(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://XKbCGtFsO6I"))
        context.startActivity(intent)
    }
}