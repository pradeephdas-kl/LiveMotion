package com.example.livemotion.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes

object WallpaperManagerUtil {

    fun setHomeWallpaper(
        context: Context,
        @DrawableRes imageRes: Int
    ) {

        try {

            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                imageRes
            )

            val wallpaperManager =
                WallpaperManager.getInstance(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    true,
                    WallpaperManager.FLAG_SYSTEM
                )

            } else {

                wallpaperManager.setBitmap(bitmap)

            }

            Toast.makeText(
                context,
                "Home wallpaper applied",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {

            Toast.makeText(
                context,
                "Failed to apply wallpaper",
                Toast.LENGTH_SHORT
            ).show()

            e.printStackTrace()

        }

    }

    fun setLockWallpaper(
        context: Context,
        @DrawableRes imageRes: Int
    ) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            Toast.makeText(
                context,
                "Lock screen wallpaper is not supported",
                Toast.LENGTH_SHORT
            ).show()

            return

        }

        try {

            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                imageRes
            )

            WallpaperManager.getInstance(context)
                .setBitmap(
                    bitmap,
                    null,
                    true,
                    WallpaperManager.FLAG_LOCK
                )

            Toast.makeText(
                context,
                "Lock wallpaper applied",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {

            Toast.makeText(
                context,
                "Failed to apply wallpaper",
                Toast.LENGTH_SHORT
            ).show()

            e.printStackTrace()

        }

    }

    fun setBothWallpapers(
        context: Context,
        @DrawableRes imageRes: Int
    ) {

        try {

            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                imageRes
            )

            val wallpaperManager =
                WallpaperManager.getInstance(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    true,
                    WallpaperManager.FLAG_SYSTEM
                )

                wallpaperManager.setBitmap(
                    bitmap,
                    null,
                    true,
                    WallpaperManager.FLAG_LOCK
                )

            } else {

                wallpaperManager.setBitmap(bitmap)

            }

            Toast.makeText(
                context,
                "Wallpaper applied",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {

            Toast.makeText(
                context,
                "Failed to apply wallpaper",
                Toast.LENGTH_SHORT
            ).show()

            e.printStackTrace()

        }

    }

}