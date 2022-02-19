package com.kldaji.android_medium

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.withStyledAttributes

class ProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.ProgressBar,
    private val doneWorks: Int = 0,
    private val totalWorks: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val progressBarRectF = RectF()
    private val backgroundRectF = RectF()
    private val textBounds = Rect()
    private val progressBarPaint = Paint()
    private val backgroundPaint = Paint()
    private val textPaint = TextPaint()
    private var progress = 0
        set(value) {
            field = value
            invalidate() // ◀ Custom Property Animation을 위해 반드시 작성해주어야 합니다.
        }
    private val text
        get() = "$progress%"

    init {
        context.withStyledAttributes(attrs,
            R.styleable.ProgressBarView,
            defStyleAttr,
            defStyleRes) {
            // ProgressBar 너비, 높이
            val progressBarWidth = getDimension(R.styleable.ProgressBarView_progressBarWidth, 0f)
            val progressBarHeight = getDimension(R.styleable.ProgressBarView_progressBarHeight, 0f)
            progressBarRectF.set(0f, 0f, progressBarWidth, progressBarHeight)
            backgroundRectF.set(0f, 0f, progressBarWidth, progressBarHeight)

            // ProgressBar Stroke 너비, 색상
            val progressBarStrokeWidth =
                getDimension(R.styleable.ProgressBarView_progressBarStrokeWidth, 0f)
            val progressColor = getColor(R.styleable.ProgressBarView_progressBarColor, 0)
            progressBarPaint.strokeWidth = progressBarStrokeWidth
            progressBarPaint.style = Paint.Style.STROKE
            progressBarPaint.color = progressColor
            backgroundPaint.strokeWidth = progressBarStrokeWidth
            backgroundPaint.style = Paint.Style.STROKE
            backgroundPaint.color = Color.GRAY

            // ProgressBar 안에 들어갈 Text 크기
            val progressTextSize = getDimension(R.styleable.ProgressBarView_progressBarTextSize, 0f)
            textPaint.textSize = progressTextSize

            // ProgressBar의 Progress
            progress = (doneWorks * 100 / totalWorks)
            startProgressBarAnimation()
        }
    }

    private fun startProgressBarAnimation() {
        ObjectAnimator.ofInt(this, "progress", 0, progress).apply {
            duration = 1500
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        textPaint.getTextBounds(text, 0, text.length, textBounds)

        // ProgressBar 안의 Text 그리기
        canvas.drawText(text,
            progressBarRectF.width() / 2 - textBounds.width() / 2,
            progressBarRectF.height() / 2 + textBounds.height() / 2,
            textPaint)
        // ProgressBar의 기본 배경 그리기
        canvas.drawArc(backgroundRectF, 270f, 360f, false, backgroundPaint)
        // ProgressBar의 Progress 그리기
        canvas.drawArc(progressBarRectF,
            270f,
            (360 * progress / 100).toFloat(),
            false,
            progressBarPaint)
    }
}
