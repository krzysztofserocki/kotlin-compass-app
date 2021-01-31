package pl.kserocki.kotlin_compass_app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {

    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(view: View, activity: Activity) {
        if (view !is EditText) {
            view.setOnTouchListener { _: View?, _: MotionEvent? ->
                hideSoftKeyboard(activity)
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView, activity)
            }
        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputMethodManager =
                activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE
                ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        }
    }

}