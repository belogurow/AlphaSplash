package ru.belogurow.alphasplash.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object DisplayUtil {

    fun getScreenWidthInPx(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }

    fun dpToPx(dpValue: Int, context: Context) = (dpValue * context.resources.displayMetrics.density).toInt()

    fun pxToDp(pxValue: Int, context: Context) = (pxValue / context.resources.displayMetrics.density).toInt()
}