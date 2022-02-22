package com.kldaji.android_medium

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.core.content.withStyledAttributes
import com.google.android.material.snackbar.Snackbar

class RouletteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val boundaryPaint = Paint()
    private val partitionPaint = Paint()
    private val rouletteViewRectF = RectF()
    private val partitions = mutableListOf<Partition>()
    private var rouletteViewWidth = 0f
    private var rouletteViewHeight = 0f

    init {
        context.withStyledAttributes(attrs, R.styleable.RouletteView, defStyleAttr, defStyleRes) {
            rouletteViewWidth = getDimension(R.styleable.RouletteView_rouletteViewWidth, 0f)
            rouletteViewHeight = getDimension(R.styleable.RouletteView_rouletteViewHeight, 0f)
            val boundaryStrokeWidth = getDimension(R.styleable.RouletteView_boundaryStrokeWidth, 0f)
            initiateBoundaryPaint(boundaryStrokeWidth)
            initiatePartitionPaint()
        }
        initiatePartitions()
    }

    private fun initiateBoundaryPaint(boundaryStrokeWidth: Float) {
        boundaryPaint.apply {
            color = Color.DKGRAY
            strokeWidth = boundaryStrokeWidth
            style = Paint.Style.STROKE
        }
    }

    private fun initiatePartitionPaint() {
        partitionPaint.apply {
            style = Paint.Style.FILL
        }
    }

    private fun initiatePartitions() {
        with(context.resources) {
            partitions.add(Partition("빨간색", getColor(R.color.red, null), 0.2f))
            partitions.add(Partition("노란색", getColor(R.color.yellow, null), 0.2f))
            partitions.add(Partition("초록색", getColor(R.color.green, null), 0.2f))
            partitions.add(Partition("파란색", getColor(R.color.blue, null), 0.2f))
            partitions.add(Partition("보라색", getColor(R.color.purple, null), 0.2f))

        }
        var startAngle = 270f
        partitions.forEach { partition ->
            val sweepAngle = 360 * partition.ratio
            partition.setAngle(startAngle, startAngle + sweepAngle)
            startAngle += sweepAngle
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        setRouletteViewRectF()
    }

    private fun setRouletteViewRectF() {
        val rouletteViewLeft = width / 2f - rouletteViewWidth / 2f
        val rouletteViewTop = height / 2f - rouletteViewHeight / 2f
        rouletteViewRectF.set(
            rouletteViewLeft,
            rouletteViewTop,
            rouletteViewWidth + rouletteViewLeft,
            rouletteViewHeight + rouletteViewTop
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        drawBoundary(canvas)
        drawPartitions(canvas)
    }

    private fun drawBoundary(canvas: Canvas) {
        canvas.drawArc(rouletteViewRectF, 270f, 360f, true, boundaryPaint)
    }

    private fun drawPartitions(canvas: Canvas) {
        partitions.forEach { partition ->
            setPartitionPaintColor(partition.color)
            canvas.drawArc(rouletteViewRectF,
                partition.startAngle,
                partition.endAngle - partition.startAngle,
                true,
                partitionPaint)
        }
    }

    private fun setPartitionPaintColor(_color: Int) {
        partitionPaint.color = _color
    }

    fun rotateRouletteView(degree: Float, _duration: Long) {
        val rotateAnimation = createRotateAnimation(degree, _duration)
        setRotateAnimationListener(rotateAnimation, degree)
        startAnimation(rotateAnimation)
    }

    private fun createRotateAnimation(degree: Float, _duration: Long): RotateAnimation {
        return RotateAnimation(0f,
            degree,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f).apply {
            duration = _duration
            fillAfter = true
            interpolator = DecelerateInterpolator(1.5f)
        }
    }

    private fun setRotateAnimationListener(rotateAnimation: RotateAnimation, degree: Float) {
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) = Unit

            override fun onAnimationEnd(animation: Animation?) {
                getResult(degree)
            }

            override fun onAnimationRepeat(animation: Animation?) = Unit
        })
    }

    private fun getResult(_degree: Float) {
        val degree = 630f - _degree % 360

        val resultCandidates = mutableListOf<Partition>()
        partitions.forEach { partition ->
            if (degree in partition.startAngle..partition.endAngle) {
                resultCandidates.add(partition)
            }
        }

        if (resultCandidates.size != 1) {
            Snackbar.make(this, "무효", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(this, resultCandidates[0].text, Snackbar.LENGTH_SHORT).show()
        }
    }
}
