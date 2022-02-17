package com.kldaji.android_medium.custom

import android.content.Context
import android.graphics.Canvas
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

class DayView @JvmOverloads constructor(
    context: Context, // ◀ context를 통해 resource, theme에 접근할 수 있습니다.
    attrs: AttributeSet? = null, // ◀ XML에 선언된 View 속성에 대한 정보를 담고 있습니다.
    defStyleAttr: Int = 0, // ◀ attrs.xml에 선언된 attr을 통해 View 속성에 기본값을 제공합니다.
    defStyleRes: Int = R.style.Calendar_Day, // ◀ View 속성에 기본값을 제공하기 위해 style resource를 선언합니다.
    private val firstDateTimeOfMonth: DateTime = DateTime(),
    private val dateTime: DateTime = DateTime(),
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    // ContextThemeWrapper: 특정 theme에 대한 새로운 ContextWrapper를 생성하는 클래스입니다.

    private val textBounds = Rect()
    private lateinit var textPaint: Paint

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            val dayTextSize =
                getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()
            textPaint = TextPaint().apply {
                textSize = dayTextSize
                color = CalendarUtils.getDayColor(dateTime.dayOfWeek) // ◀ 토요일은 파란색, 일요일은 빨간색으로 설정합니다.
                if (!CalendarUtils.isSameMonth(dateTime, firstDateTimeOfMonth)) alpha = 50 // ◀ 현재 "월"에 속하는 날짜가 아니면 투명도를 설정합니다.
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        val day = dateTime.dayOfMonth.toString() // ◀ 날짜 정보를 가져옵니다.
        textPaint.getTextBounds(day, 0, day.length, textBounds)
        canvas.drawText(
            day,
            (width / 2 - textBounds.width() / 2).toFloat(),
            (height / 4 + textBounds.height()).toFloat(),
            textPaint
        )
    }
}
