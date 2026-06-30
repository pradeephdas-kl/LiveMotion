package com.example.livemotion.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes
import java.net.URL

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

            val wallpaperManager = WallpaperManager.getInstance(context)

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

            e.printStackTrace()

            Toast.makeText(
                context,
                e.message ?: "Unknown Error",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    fun setHomeWallpaperFromUrl(
        context: Context,
        imageUrl: String
    ) {

        try {

            val url = URL(imageUrl)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            val wallpaperManager = WallpaperManager.getInstance(context)

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


        } catch (e: Exception) {

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
                "Lock screen not supported",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        try {

            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                imageRes
            )

            WallpaperManager.getInstance(context).setBitmap(
                bitmap,
                null,
                true,
                WallpaperManager.FLAG_LOCK
            )


        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(
                context,
                e.message ?: "Unknown Error",
                Toast.LENGTH_LONG
            ).show()

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

            val wallpaperManager = WallpaperManager.getInstance(context)

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

            e.printStackTrace()

            Toast.makeText(
                context,
                e.message ?: "Unknown Error",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    fun setLockWallpaperFromUrl(
        context: Context,
        imageUrl: String
    ) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            Toast.makeText(
                context,
                "Lock screen not supported",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        try {

            val url = URL(imageUrl)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            WallpaperManager.getInstance(context).setBitmap(
                bitmap,
                null,
                true,
                WallpaperManager.FLAG_LOCK
            )


        } catch (e: Exception) {

            e.printStackTrace()



        }

    }

    fun setBothWallpapersFromUrl(
        context: Context,
        imageUrl: String
    ) {

        try {

            val url = URL(imageUrl)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            val wallpaperManager = WallpaperManager.getInstance(context)

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

            e.printStackTrace()



        }

    }

}