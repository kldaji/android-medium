package com.kldaji.android_medium

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.kldaji.android_medium.custom.DateView
import com.kldaji.android_medium.custom.DayView
import com.kldaji.android_medium.data.Day
import org.joda.time.DateTime

class CalendarViewModel : ViewModel() {
    private val calendarMap = mutableMapOf<Long, MutableList<View>>()

//    fun hasDayAndDateViews(millis: Long) = calendarMap.containsKey(millis)

    fun addDayAndDateViews(
        calendarView: ViewGroup,
        millis: Long,
        dateTimes: List<DateTime>,
    ) {
        val dayAndDateViews = mutableListOf<View>()
        dayAndDateViews.addAll(addDayViews(calendarView))
        dayAndDateViews.addAll(addDateViews(calendarView, DateTime(millis), dateTimes))
        calendarMap[millis] = dayAndDateViews
    }

    private fun addDayViews(calendarView: ViewGroup): List<View> {
        val views = mutableListOf<View>()
        Day.values().forEach {
            val dayView = DayView(context = calendarView.context, day = it.day)
            calendarView.addView(dayView)
            views.add(dayView)
        }
        return views
    }

    private fun addDateViews(
        calendarView: ViewGroup,
        firstDateTimeOfMonth: DateTime,
        dateTimes: List<DateTime>,
    ): List<View> {
        val views = mutableListOf<View>()
        dateTimes.forEach {
            val dateView = DateView(
                context = calendarView.context,
                firstDateTimeOfMonth = firstDateTimeOfMonth,
                dateTime = it
            )
            calendarView.addView(dateView)
            views.add(dateView)
        }
        return views
    }
}
