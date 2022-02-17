package com.kldaji.android_medium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kldaji.android_medium.CalendarUtils.Companion.getDateTimes
import com.kldaji.android_medium.databinding.FragmentCalendarBinding
import org.joda.time.DateTime

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var millis: Long = 0L
    private val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            millis = it.getLong(MILLIS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstDateTimeOfMonth = DateTime(millis)
//        binding.cvCalendar.initCalendar(firstDateTimeOfMonth, getDateTimes(DateTime(millis)))
        calendarViewModel.addDayAndDateViews(binding.cvCalendar, millis, getDateTimes(DateTime(millis)))
        binding.tbCalendar.title = firstDateTimeOfMonth.toString("yyyy년 MM월")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = CalendarFragment().apply {
            arguments = Bundle().apply {
                putLong(MILLIS, millis)
            }
        }
    }
}
