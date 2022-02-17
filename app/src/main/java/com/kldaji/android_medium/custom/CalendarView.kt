package com.kldaji.android_medium.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.kldaji.android_medium.CalendarUtils
import com.kldaji.android_medium.R
import com.kldaji.android_medium.data.Day
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private var _dayHeight = 0f
    private var _dateHeight = 0f

    init {
        // dayHeight와 dateHeight는 CalendarView가 선언된 XML 파일에 값이 할당되어 있습니다.
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _dayHeight = getDimension(R.styleable.CalendarView_dayHeight, 0f)
            _dateHeight = getDimension(R.styleable.CalendarView_dateHeight, 0f)
        }
    }

    fun initCalendar(firstDateTimeOfMonth: DateTime, dateTimes: List<DateTime>) {
        addDayViews()
        addDateViews(firstDateTimeOfMonth, dateTimes)
    }

    private fun addDayViews() {
        Day.values().forEach {
            addView(DayView(context = context, day = it.day))
        }
    }

    private fun addDateViews(firstDateTimeOfMonth: DateTime, dateTimes: List<DateTime>) {
        dateTimes.forEach {
            addView(DateView(
                context = context,
                firstDateTimeOfMonth = firstDateTimeOfMonth,
                dateTime = it
            ))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getDefaultSize(minimumWidth,
            widthMeasureSpec) // ◀ 부모 ViewGroup의 measureSpec 값을 통해 해당 ViewGroup이 가질 수 있는 width 값을 계산합니다.
        val height = (_dayHeight + _dateHeight * CalendarUtils.WEEKS_PER_MONTH).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childWidth = (width / DateTimeConstants.DAYS_PER_WEEK).toFloat()
        var dayIndex = 0
        var dateIndex = 0
        children.forEachIndexed { i, view ->
            if (i <= 6) {
                setDayViewsLayout(dayIndex, view, childWidth)
                dayIndex += 1
            } else {
                setDateViewsLayout(dateIndex, view, childWidth)
                dateIndex += 1
            }
        }
    }

    private fun setDayViewsLayout(index: Int, view: View, childWidth: Float) {
        val left = index * childWidth
        view.layout(
            left.toInt(),
            0,
            (left + childWidth).toInt(),
            _dayHeight.toInt()
        )
    }

    private fun setDateViewsLayout(index: Int, view: View, childWidth: Float) {
        val dateHeight = (height - _dayHeight) / CalendarUtils.WEEKS_PER_MONTH
        val left = (index % DateTimeConstants.DAYS_PER_WEEK) * childWidth
        val top = (index / DateTimeConstants.DAYS_PER_WEEK) * dateHeight + _dayHeight
        view.layout(
            left.toInt(),
            top.toInt(),
            (left + childWidth).toInt(),
            (top + dateHeight).toInt()
        )
    }
}
