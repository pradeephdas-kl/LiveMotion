package com.example.livemotion.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.DrawableRes
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object WallpaperUtils {

    fun saveWallpaper(
        context: Context,
        @DrawableRes imageRes: Int
    ): Boolean {

        return try {

            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                imageRes
            )

            val fileName = "LiveMotion_${System.currentTimeMillis()}.jpg"

            var outputStream: OutputStream? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_DOWNLOADS + "/LiveMotion"
                    )
                }

                val uri = context.contentResolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    values
                )

                outputStream = uri?.let {
                    context.contentResolver.openOutputStream(it)
                }

            } else {

                val folder = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ),
                    "LiveMotion"
                )

                if (!folder.exists())
                    folder.mkdirs()

                val file = File(folder, fileName)

                outputStream = FileOutputStream(file)
            }

            outputStream?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }

            true

        } catch (e: Exception) {

            e.printStackTrace()
            false

        }

    }

}