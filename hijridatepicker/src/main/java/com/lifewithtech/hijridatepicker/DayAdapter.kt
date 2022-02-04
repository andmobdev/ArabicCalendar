package com.lifewithtech.hijridatepicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sodainmind.hijridatepicker.R
import com.sodainmind.hijridatepicker.databinding.ViewDayBinding

class DayAdapter(private val list: MutableList<String>
) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {



    class ViewHolder(val binding: ViewDayBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.view_day, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvDay.text = list[position]

    }

    override fun getItemCount(): Int {
        return list.size
    }


}