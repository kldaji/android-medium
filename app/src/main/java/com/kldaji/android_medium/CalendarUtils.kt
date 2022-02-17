package com.kldaji.android_medium

import android.graphics.Color
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class CalendarUtils {

    companion object {
        const val WEEKS_PER_MONTH = 6

        fun getDateTimes(dateTime: DateTime): List<DateTime> {
            val dateTimes = mutableListOf<DateTime>()
            val firstDateTimeOfMonth = dateTime.withDayOfMonth(1)
            val prevOffset = getPrevOffSet(firstDateTimeOfMonth)
            val startDateTime = firstDateTimeOfMonth.minusDays(prevOffset)
            for (i in 0 until DateTimeConstants.DAYS_PER_WEEK * WEEKS_PER_MONTH) {
                dateTimes.add(DateTime(startDateTime.plusDays(i)))
            }
            return dateTimes
        }

        private fun getPrevOffSet(dateTime: DateTime): Int {
            var prevOffset = dateTime.dayOfWeek
            if (prevOffset >= 7) prevOffset %= 7
            return prevOffset
        }

        fun isSameMonth(first: DateTime, second: DateTime): Boolean =
            first.year == second.year && first.monthOfYear == second.monthOfYear

        fun getDateColor(dayOfWeek: Int): Int {
            return when (dayOfWeek) {
                DateTimeConstants.SATURDAY -> Color.BLUE
                DateTimeConstants.SUNDAY -> Color.RED
                else -> Color.BLACK
            }
        }

        fun getDayColor(day: String): Int {
            return when (day) {
                "토" -> Color.BLUE
                "일" -> Color.RED
                else -> Color.BLACK
            }
        }
    }
}
