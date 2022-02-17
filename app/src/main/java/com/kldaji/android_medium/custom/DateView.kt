package com.kldaji.android_medium.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import androidx.core.content.withStyledAttributes
import com.kldaji.android_medium.CalendarUtils
import com.kldaji.android_medium.R
import org.joda.time.DateTime

class DateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Calendar_Date,
    private val firstDateTimeOfMonth: DateTime = DateTime(),
    private val dateTime: DateTime = DateTime(),
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private val textBounds = Rect()
    private val textPaint = TextPaint()
    private val linePaint = Paint()

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            val dateTextSize =
                getDimensionPixelSize(R.styleable.CalendarView_dateTextSize, 0).toFloat()
            textPaint.apply {
                textSize = dateTextSize
                color =
                    CalendarUtils.getDateColor(dateTime.dayOfWeek) // ◀ 토요일은 파란색, 일요일은 빨간색으로 설정합니다.
                if (!CalendarUtils.isSameMonth(dateTime, firstDateTimeOfMonth))
                    alpha = 50 // ◀ 현재 "월"에 속하는 날짜가 아니면 투명도를 설정합니다.
            }
            linePaint.apply {
                color = Color.GRAY
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        val date = dateTime.dayOfMonth.toString() // ◀ 날짜 정보를 가져옵니다.
        textPaint.getTextBounds(date, 0, date.length, textBounds)
        canvas.drawText(
            date,
            (width / 2 - textBounds.width() / 2).toFloat(),
            (height / 4 + textBounds.height()).toFloat(),
            textPaint
        )
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, linePaint)
    }
}
