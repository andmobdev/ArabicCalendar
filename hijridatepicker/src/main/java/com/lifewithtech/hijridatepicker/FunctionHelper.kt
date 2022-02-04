package com.lifewithtech.hijridatepicker

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

object FunctionHelper {

    val hijriMonth = arrayListOf(
        HijriMonth.SHEHRE_MOHARRAMUL_HARAM,
        HijriMonth.SAFARUL_MUZAFFAR,
        HijriMonth.RABIUL_AWWAL,
        HijriMonth.RABIUL_AKHAR,
        HijriMonth.JAMADAL_ULA,
        HijriMonth.JAMADAL_UKHRA,
        HijriMonth.SHEHRE_RAJABUL_ASAB,
        HijriMonth.SHABANUL_KARIM,
        HijriMonth.SHEHRE_RAMAZANUL_MOAZZAM,
        HijriMonth.SHAWWALUL_MUKARRAM,
        HijriMonth.ZILQADATIL_HARAM,
        HijriMonth.ZILHIJJATIL_HARAM
    )

    val days = arrayListOf("S", "M", "T", "W", "T", "F", "S")

    object HijriMonth {
        const val SHEHRE_MOHARRAMUL_HARAM = "Shehre Moharramul Haram"
        const val SAFARUL_MUZAFFAR = "Safarul Muzaffar"
        const val RABIUL_AWWAL = "Rabiul Awwal"
        const val RABIUL_AKHAR = "Rabiul Akhar"
        const val JAMADAL_ULA = "Jamadal Ula"
        const val JAMADAL_UKHRA = "Jamadal Ukhra"
        const val SHEHRE_RAJABUL_ASAB = "Shehre Rajabul Asab"
        const val SHABANUL_KARIM = "Shabanul Karim"
        const val SHEHRE_RAMAZANUL_MOAZZAM = "Shehre Ramazanul Moazzam"
        const val SHAWWALUL_MUKARRAM = "Shawwalul Mukarram"
        const val ZILQADATIL_HARAM = "Zilqadatil Haram"
        const val ZILHIJJATIL_HARAM = "Zilhijjatil Haram"
    }

    fun Calendar.convertStringFormat(stringFormat: String): String {
        val df = SimpleDateFormat(stringFormat, Locale.US)
        return df.format(this.time)
    }

    fun getGregDate(hijriDate: IntArray): Calendar {
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = 1
        calendar[Calendar.MONTH] = Calendar.JANUARY
        calendar[Calendar.YEAR] = 2005
        var trialHijriDate = getHijriDate(Date(calendar.timeInMillis))
        var yearDifference: Int
        var yearDifferenceInMillis: Long
        var monthDifference: Int
        var monthDifferenceInMillis: Long
        var dayDifference: Int
        var dayDifferenceInMillis: Long
        var n = 0
        while (!trialHijriDate.contentEquals(hijriDate) && n < 5) {
            yearDifference = hijriDate[2] - trialHijriDate[2]
            yearDifferenceInMillis = yearDifference.toLong() * 30617280000L
            monthDifference = hijriDate[1] - trialHijriDate[1]
            monthDifferenceInMillis = monthDifference.toLong() * 2551440000L
            dayDifference = hijriDate[0] - trialHijriDate[0]
            dayDifferenceInMillis = dayDifference.toLong() * 86400000L
            calendar.timeInMillis =
                calendar.timeInMillis + yearDifferenceInMillis + monthDifferenceInMillis + dayDifferenceInMillis
            trialHijriDate = getHijriDate(Date(calendar.timeInMillis))
            n++
        }
        return calendar
    }

    fun getHijriDate(date: Date): IntArray {
        val cal = Calendar.getInstance()
        cal.time = date
        var year = cal[Calendar.YEAR].toDouble()
        var month = cal[Calendar.MONTH].toDouble()
        var day = cal[Calendar.DAY_OF_MONTH].toDouble()
        var m = month + 1
        var y = year
        if (m < 3) {
            y -= 1
            m += 12
        }
        var a = floor(year / 100.0)
        var b = 2 - a + floor(a / 4.0)
        if (y < 1583) b = 0.0
        if (y == 1582.0) {
            if (m > 10) b = -10.0
            if (m == 10.0) {
                b = 0.0
                if (day > 4) b = -10.0
            }
        }
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524
        b = 0.0
        if (jd > 2299160) {
            a = floor((jd - 1867216.25) / 36524.25)
            b = 1 + a - floor(a / 4.0)
        }
        val bb = jd + b + 1524
        var cc = floor((bb - 122.1) / 365.25)
        val dd = floor(365.25 * cc)
        val ee = floor((bb - dd) / 30.6001)
        day = bb - dd - floor(30.6001 * ee)
        month = ee - 1
        if (ee > 13) {
            cc += 1
            month = ee - 13
        }
        year = cc - 4716
        val iYear = 10631.0 / 30.0
        val epochastro = 1948084.0
        val shift3 =
            0.01 / 60.0 // results in years 2, 5, 8, 10, 13, 16, 19, 21, 24, 27 & 29 as leap years
        var z = jd - epochastro
        val cyc = floor(z / 10631.0)
        z -= 10631 * cyc
        val j = floor((z - shift3) / iYear)
        val iy = 30 * cyc + j
        z -= floor(j * iYear + shift3)
        var im = floor((z + 28.5001) / 29.5)
        if (im == 13.0) im = 12.0
        val id = z - floor(29.5001 * im - 29)
        im -= 1
        return intArrayOf(id.toInt(), im.toInt(), iy.toInt())
    }

    fun getMonthSize(_month: Int, year: Int): Int {
        var month = _month
        Log.v("JPC", "Month:\t\t$month")
        Log.v("JPC", "Year:\t\t$year")
        month++
        return if (month % 2 != 0 || month == 12 && isLeap(year)) 30 else 29
    }

    private fun isLeap(mYear: Int): Boolean {
        var year = mYear
        year %= 30
        return year == 2 || year == 5 || year == 8 || year == 10 || year == 13 || year == 16 || year == 19 || year == 21 || year == 24 || year == 27 || year == 29
    }

    fun String.getArabicNumber(): String {
        val arabicChars = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        val builder = StringBuilder()
        for (i in this.indices) {
            if (Character.isDigit(this[i])) {
                builder.append(arabicChars[this[i].code - 48])
            } else {
                builder.append(this[i])
            }
        }

        return builder.toString()
    }

}