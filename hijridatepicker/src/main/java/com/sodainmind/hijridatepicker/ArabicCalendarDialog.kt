package com.sodainmind.hijridatepicker

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import java.util.*
import kotlin.collections.ArrayList
import androidx.databinding.DataBindingUtil
import com.sodainmind.hijridatepicker.databinding.DialogCalendarBinding
import android.view.ViewGroup


class ArabicCalendarDialog(private var onItemClick: (HijriObj) -> Unit,date: Calendar) : DialogFragment() {

    private var selectedDate: HijriObj = HijriObj()
    private lateinit var adapter: CalendarDateAdapter
    private var todayHijriDate: IntArray

    init {
        val today = date
        todayHijriDate = FunctionHelper.getHijriDate(today.time)

        selectedDate = getHijriDate(today)

    }

    companion object {
        private const val DATE = 0
        private const val MONTH: Int = 1
        private const val YEAR: Int = 2
    }

    private lateinit var binding: DialogCalendarBinding

    override fun onCreateView(
        i: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            i, R.layout.dialog_calendar, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialog = this
        binding.rvData.itemAnimator = null
        adapter = CalendarDateAdapter(onItemClick = { date ->
            selectedDate = date
            binding.hijriObj = selectedDate
        }, mutableListOf())

        val dayAdapter = DayAdapter(FunctionHelper.days)
        binding.rvDay.adapter = dayAdapter
        binding.rvData.adapter = adapter


        binding.hijriObj = selectedDate

        val data = updateCurrentMonth(todayHijriDate)
        adapter.updateList(data)
    }


    private fun getHijriDate(date: Calendar): HijriObj {
        val hijriDate = HijriObj()
        val d = FunctionHelper.getHijriDate(date.time)
        hijriDate.day = d[DATE]
        hijriDate.month = d[MONTH]
        hijriDate.year = d[YEAR]
        return hijriDate
    }

    fun previous() {
        selectedDate.month--
        binding.tvCurrentMonthYear.text = "${selectedDate.monthName} ${selectedDate.year}"
        val data =
            updateCurrentMonth(intArrayOf(selectedDate.day, selectedDate.month, selectedDate.year))
        adapter.updateList(data)
    }

    fun next() {
        selectedDate.month++
        binding.tvCurrentMonthYear.text = "${selectedDate.monthName} ${selectedDate.year}"
        val data =
            updateCurrentMonth(intArrayOf(selectedDate.day, selectedDate.month, selectedDate.year))
        adapter.updateList(data)
    }

    fun close() {
        this.dismiss()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
        }
    }

    fun save() {
        onItemClick(selectedDate)
        this.dismiss()
    }

    private fun updateCurrentMonth(hijriDate: IntArray): ArrayList<HijriObj> {
        try {
            val allHijriDates: ArrayList<HijriObj> = ArrayList()
            hijriDate[DATE] = 1
            val gregDate: Calendar = FunctionHelper.getGregDate(hijriDate)

            val monthBeginningCell = gregDate[Calendar.DAY_OF_WEEK] - 1
            var monthSize = FunctionHelper.getMonthSize(hijriDate[MONTH], hijriDate[YEAR])
            monthSize += monthBeginningCell
            val totalCell: Int
            var minusCell: Int
            if (monthSize > 35) {
                totalCell = 42
                minusCell = 42 - monthSize
                minusCell += 1
            } else {
                totalCell = 35
                minusCell = 35 - monthSize
                minusCell += 1
            }
            for (i in 0 until totalCell) {
                when {
                    i < monthBeginningCell -> {
                        val obj = HijriObj()
                        obj.displayCell = false
                        allHijriDates.add(obj)
                    }
                    i >= monthSize -> {
                        val obj = HijriObj()
                        obj.displayCell = false
                        allHijriDates.add(obj)
                    }
                    else -> {
                        val obj = HijriObj()
                        obj.day = hijriDate[DATE]
                        obj.month = hijriDate[MONTH]
                        obj.year = hijriDate[YEAR]


                        obj.displayCell = true
                        allHijriDates.add(obj)
                        hijriDate[DATE] += 1
                    }
                }
            }

            return allHijriDates

        } catch (e: Exception) {
            e.printStackTrace()
            return ArrayList()
        }
    }

}