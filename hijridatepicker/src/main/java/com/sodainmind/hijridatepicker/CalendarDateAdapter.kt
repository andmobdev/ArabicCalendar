package com.sodainmind.hijridatepicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sodainmind.hijridatepicker.databinding.ViewCalendarCellBinding

class CalendarDateAdapter(
    private var onItemClick: (HijriObj) -> Unit, private val list: MutableList<HijriObj>
) : RecyclerView.Adapter<CalendarDateAdapter.ViewHolder>() {


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
    }

    fun selectCell(item: HijriObj) {
        onItemClick(item)
    }

    fun updateList(list: ArrayList<HijriObj>) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getItemCount(): Int {
        return list.size

    }

}