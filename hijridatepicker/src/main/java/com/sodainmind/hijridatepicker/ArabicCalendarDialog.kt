package com.sodainmind.hijridatepicker


import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class ArabicCalendarDialog() : DialogFragment() {
    private var selectedDate: HijriObj = HijriObj()
    private lateinit var adapter: CalendarDateAdapter
    private lateinit var todayHijriDate: IntArray
    //private lateinit var binding: DialogCalendarBinding
    private var data = ArrayList<HijriObj>()
    private var listener: OnDateSelectedListener? = null
    private var start = 1350
    private var end = 1500
    private  lateinit var rvDay:RecyclerView
    private lateinit var rvData:RecyclerView
    private lateinit var ivPrevious:ImageView
    private lateinit var ivNext:ImageView
    private lateinit var tvCurrentMonthYear:TextView
    private lateinit var tvCancelButton:TextView
    private lateinit var tvOkButton:TextView
    private lateinit var tvArabicDate:TextView
    private lateinit var tvGeorgDate:TextView
    private var layout=R.layout.dialog_calendar


    init {
        setDate(Calendar.getInstance())
    }
    companion object {
        private const val DATE = 0
        private const val MONTH: Int = 1
        private const val YEAR: Int = 2
    }


    override fun onCreateView(
        i: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val view: View = i.inflate(layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvData=view.findViewById(R.id.rv_data)
        rvDay=view.findViewById(R.id.rv_day)
        ivPrevious=view.findViewById(R.id.iv_prev_month)
        ivNext=view.findViewById(R.id.iv_next_month)
        tvCurrentMonthYear=view.findViewById(R.id.tv_current_month_year)
        tvCancelButton=view.findViewById(R.id.tv_cancel)
        tvOkButton=view.findViewById(R.id.tv_ok_submit)
        tvArabicDate=view.findViewById(R.id.tv_arabic_date)
        tvGeorgDate=view.findViewById(R.id.tv_greg_date_calendar)
        rvData.itemAnimator=null
        adapter = CalendarDateAdapter(onItemClick = { date ->
            selectedDate = date
            tvArabicDate.text=selectedDate.arabicDate
            tvGeorgDate.text=selectedDate.gregDate
        }, mutableListOf())

        val dayAdapter = DayAdapter(FunctionHelper.days)
        rvDay.adapter = dayAdapter
        rvData.adapter = adapter

        tvCurrentMonthYear.text = "${selectedDate.monthName} ${selectedDate.year}"
        tvArabicDate.text=selectedDate.arabicDate
        tvGeorgDate.text=selectedDate.gregDate

        data = updateCurrentMonth(todayHijriDate)
        adapter.updateList(data)
        ivPrevious.setOnClickListener{previous()}
        ivNext.setOnClickListener({next()})
        tvCancelButton.setOnClickListener({close()})
        tvOkButton.setOnClickListener({save()})

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
        if (selectedDate.year >= start) {
            selectedDate.month--
            changeCurrentMonth(selectedDate)
        }
    }

    private fun changeCurrentMonth(selectedDate: HijriObj) {
        tvCurrentMonthYear.text = "${selectedDate.monthName} ${selectedDate.year}"
        data =
            updateCurrentMonth(
                intArrayOf(
                    selectedDate.day,
                    selectedDate.month,
                    selectedDate.year
                )
            )
        adapter.updateList(data)
    }

    fun next() {
        if (selectedDate.year <= end) {
            selectedDate.month++
            changeCurrentMonth(selectedDate)
        }
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
        if (listener != null) {
            listener!!.onClick(selectedDate)
        }
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

    private fun setDate(today: Calendar): ArabicCalendarDialog {
        todayHijriDate = FunctionHelper.getHijriDate(today.time)
        selectedDate = getHijriDate(today)
        data = updateCurrentMonth(todayHijriDate)
        return this

    }

    /**
     * setCurrentDate() method allows to set the date of opening of calendar instead of default date which is current date.
     * @param today it contains the date as calendar instance which sets the date of calendar as per requirement.
     */
    fun setCurrentDate(today: Calendar): ArabicCalendarDialog {
        setDate(today)
        return this

    }

    /**
     * setOnDateListener() this method provides the user value of selected user input in calendar after successful selection.
     * @param listener it is a interface which provide the selected value value from the Hijri calendar.
     */
    fun setOnDateSetListener(listener: OnDateSelectedListener): ArabicCalendarDialog {
        this.listener = listener
        return this

    }

    /**
     * setYearRange() method is used to set custom working range of Hijri calendar
     * @param start uses to assign the starting year of calendar
     * @param end uses to assign the ending year of the calendar
     */
    fun setYearRange(start: Int, end: Int): ArabicCalendarDialog {
        this.start = start
        this.end = end
        return this
    }

    /**
     *setHijriMonthArray() method uses a string array to clear the existing array of month with new user defined arraylist.
     * @param hijriMonthArray is used to assign the Hijri month defined by user instead of default
     */
    fun setHijriMonthArray(hijriMonthArray: ArrayList<String>): ArabicCalendarDialog {
        if (hijriMonthArray.size == 12) {
            FunctionHelper.hijriMonth.clear()
            FunctionHelper.hijriMonth.addAll(hijriMonthArray)
        }
        return this
    }

    fun setCustomCalendarDesign(layout:Int): ArabicCalendarDialog {
         this.layout=layout
        return this
    }

}