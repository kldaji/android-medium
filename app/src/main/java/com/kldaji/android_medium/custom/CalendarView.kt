package com.kldaji.android_medium.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.kldaji.android_medium.CalendarUtils
import com.kldaji.android_medium.R
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private var _height = 0f

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _height = getDimension(R.styleable.CalendarView_dayHeight, 0f)
        }
    }

    fun initCalendar(firstDateTimeOfMonth: DateTime, dateTimes: List<DateTime>) {
        dateTimes.forEach {
            addView(DayView(
                context = context,
                firstDateTimeOfMonth = firstDateTimeOfMonth,
                dateTime = it
            ))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getDefaultSize(minimumWidth,
            widthMeasureSpec) // ◀ 부모 ViewGroup의 measureSpec 값을 통해 해당 ViewGroup이 가질 수 있는 width 값을 계산합니다.
        val height = (_height * CalendarUtils.WEEKS_PER_MONTH).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val dayItemWidth = (width / DateTimeConstants.DAYS_PER_WEEK).toFloat()
        val dayItemHeight = (height / CalendarUtils.WEEKS_PER_MONTH).toFloat()
        var index = 0
        children.forEach { dayView ->
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * dayItemWidth
            val top = (index / DateTimeConstants.DAYS_PER_WEEK) * dayItemHeight
            dayView.layout(
                left.toInt(),
                top.toInt(),
                (left + dayItemWidth).toInt(),
                (top + dayItemHeight).toInt()
            )
            index++
        }
    }
}
