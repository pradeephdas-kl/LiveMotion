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
import java.net.URL
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import android.app.WallpaperManager
import android.widget.Toast

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

    fun saveWallpaperFromUrl(
        context: Context,
        imageUrl: String
    ): Boolean {

        return try {

            val url = URL(imageUrl)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

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

    fun openSystemWallpaperPicker(
        context: Context,
        imageUrl: String
    ) {

        try {

            val bitmap = BitmapFactory.decodeStream(
                URL(imageUrl).openStream()
            )

            val file = File(
                context.cacheDir,
                "wallpaper.jpg"
            )

            FileOutputStream(file).use {
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    it
                )
            }

            val uri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                file
            )

            val intent = Intent(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER).apply {
                setDataAndType(uri, "image/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(intent)

        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(
                context,
                "Unable to open wallpaper picker",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

}