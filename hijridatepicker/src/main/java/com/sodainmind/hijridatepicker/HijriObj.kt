package com.sodainmind.hijridatepicker

import com.sodainmind.hijridatepicker.FunctionHelper.convertStringFormat
import com.sodainmind.hijridatepicker.FunctionHelper.getArabicNumber

import java.util.*


data class HijriObj(
    var day: Int = 0,
    var year: Int = 0,
    var displayCell: Boolean = false,
    var isEventDay: Boolean = false,
    var setSelectedDay: Boolean = false,
    var showMonth: Boolean = false,
) {
    var month: Int = 0
        set(value) {
            field = when (value) {
                -1 -> {
                    year--
                    11
                }
                12 -> {
                    year++
                    0
                }
                else -> {
                    value
                }
            }
        }

    val monthName: String
        get() = FunctionHelper.hijriMonth[month]

    val arabicDate: String
        get() = "$day $monthName $year"

    val gregDate: String
        get() = gregCalendar.convertStringFormat("dd MMM yyyy")

    val gregCalendar: Calendar
        get() = FunctionHelper.getGregDate(intArrayOf(day, month, year))

    val arabicNumber: String
        get() = day.toString().getArabicNumber()

}

