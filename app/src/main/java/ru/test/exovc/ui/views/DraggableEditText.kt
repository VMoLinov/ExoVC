package ru.test.exovc.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class DraggableEditText(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    var dX = 0f
    var dY = 0f
    var lastActionTime = System.currentTimeMillis()

    init {
        setOnTouchListener(TouchListener())
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        super.onKeyPreIme(keyCode, event)
        if (keyCode == KeyEvent.KEYCODE_BACK) clearFocus()
        return false
    }

    inner class TouchListener : OnTouchListener {

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val parentView = parent as View
            val maxHeight = parentView.height.toFloat() - v.height
            val maxWidth = parentView.width.toFloat() - v.width
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = v.x - event.rawX
                    dY = v.y - event.rawY
                    lastActionTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_MOVE -> {
                    val newY = event.rawY + dY
                    val newX = event.rawX + dX
                    v.y = when {
                        (newY < 0) -> 0f
                        (newY > maxHeight) -> maxHeight
                        else -> newY
                    }
                    v.x = when {
                        (newX < 0) -> 0f
                        (newX > maxWidth) -> maxWidth
                        else -> newX
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (lastActionTime + 1000L > System.currentTimeMillis()) {
                        v.performClick()
                        v.requestFocus()
                        return false
                    }
                }
            }
            return true
        }
    }
}
