package com.lifewithtech.arabiccalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    var dateTest = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv_click)
        tv.setOnClickListener {
            com.sodainmind.hijridatepicker.ArabicCalendarDialog(onItemClick = {
                Toast.makeText(this, it.gregDate, Toast.LENGTH_LONG).show()

            }, dateTest).show(supportFragmentManager, "DIALOG")
        }
    }
}