package com.lifewithtech.arabiccalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.sodainmind.hijridatepicker.ArabicCalendarDialog
import com.sodainmind.hijridatepicker.HijriObj
import com.sodainmind.hijridatepicker.OnDateSelectedListener
import java.util.*

class MainActivity : AppCompatActivity() {
    var dateTest = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv_click)
        dateTest.set(Calendar.DATE, 10)
        tv.setOnClickListener {
            ArabicCalendarDialog()
                .setOnDateSetListener(object : OnDateSelectedListener {
                    override fun onClick(date: HijriObj) {
                        Toast.makeText(this@MainActivity, date.gregDate, Toast.LENGTH_SHORT).show()
                    }
                })
                .show(supportFragmentManager, "DIALOG")
        }
    }
}