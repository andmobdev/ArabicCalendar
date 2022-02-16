package com.lifewithtech.hijridatepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lifewithtech.hijridatepicker.databinding.ViewCalendarCellBinding

class CalendarDateAdapter(
    private var onItemClick: (HijriObj) -> Unit, private val list: MutableList<HijriObj>
) : RecyclerView.Adapter<CalendarDateAdapter.ViewHolder>() {

    private var view: View? = null

    private var selectedDate: HijriObj? = null

    class ViewHolder(val binding: ViewCalendarCellBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.view_calendar_cell, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.adapter = this
        holder.binding.hijriDate = item

        if (item.gregDate == selectedDate?.gregDate) {
            view = holder.binding.root.findViewById(R.id.rl_day_bg)
            holder.binding.rlDayBg.isSelected = true
            holder.binding.rlDayBg.setBackgroundResource(R.drawable.cell_border)
        } else {
            holder.binding.rlDayBg.setBackgroundResource(R.drawable.normal_cell_border)
        }
    }

    fun setCurrentSelectedDate(selectedDate: HijriObj): CalendarDateAdapter {
        this.selectedDate = selectedDate
        return this
    }


    fun selectCell(v: View, item: HijriObj) {
        onItemClick(item)
        selectedDate?.day = item.day
        selectedDate?.month = item.month
        selectedDate?.year = item.year
        //selectedDate=item
        if (v.isSelected) {
            v.isSelected = false
            v.setBackgroundResource(R.drawable.normal_cell_border)

        } else {
            v.isSelected = true
            v.setBackgroundResource(R.drawable.cell_border)
            if (v != view) {
                view?.isSelected = false
                view?.setBackgroundResource(R.drawable.normal_cell_border)
            }
        }
        view = v

    }

    fun updateList(list: ArrayList<HijriObj>) {
        view?.setBackgroundResource(R.drawable.normal_cell_border)
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getItemCount(): Int {
        return list.size

    }

}