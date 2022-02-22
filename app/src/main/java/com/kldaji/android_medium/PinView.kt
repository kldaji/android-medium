package com.kldaji.android_medium

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.View
import androidx.core.content.withStyledAttributes

class PinView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.PinView,
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private var rouletteViewRadius = 0f
    private var toolBarHeight = 0
    private val pinViewRect = Rect()
    var pinBitmap: Bitmap? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.PinView, defStyleAttr, defStyleRes) {
            rouletteViewRadius = getDimension(R.styleable.PinView_rouletteViewRadius, 0f)
        }
        setToolBarHeight()
    }

    private fun setToolBarHeight() {
        val typedValue = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            toolBarHeight =
                TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        setPinViewRect()
    }

    private fun setPinViewRect() {
        pinBitmap?.let {
            val pinViewWidth = rootView.width / 2 - it.width / 2
            val pinViewHeight =
                rootView.height / 2 - toolBarHeight - rouletteViewRadius + it.height / 4

            pinViewRect.set(
                pinViewWidth,
                pinViewHeight.toInt(),
                pinViewWidth + it.width,
                (pinViewHeight + it.height).toInt()
            )
        }

    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        pinBitmap?.let {
            canvas.drawBitmap(it, null, pinViewRect, null)
        }
    }
}
