package com.lifewithtech.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.lifewithtech.hijridatepicker.ArabicCalendarDialog
import com.lifewithtech.hijridatepicker.HijriObj
import com.lifewithtech.hijridatepicker.OnDateSelectedListener
import java.util.*

class MainActivity : AppCompatActivity() {
    private var dateTest: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv_click)
        dateTest.set(Calendar.DATE, 10)
        val hijriMonth = arrayListOf(
            "Test-1", "Test-1", "Test-1", "Test-1", "Test-1", "Test-1", "Test-1", "Test-1","Test-1", "Test-1","Test-1"
        )
        /*tv.setOnClickListener {
            ArabicCalendarDialog()
                .setOnDateSetListener(object : OnDateSelectedListener {
                    override fun onClick(date: HijriObj) {
                        Toast.makeText(this@MainActivity, date.gregDate, Toast.LENGTH_SHORT).show()
                    }
                })
                .show(supportFragmentManager, "DIALOG")
        }*/

        val button: Button = findViewById<Button>(R.id.btn_submit)
        button.setOnClickListener({
            ArabicCalendarDialog()
                .setOnDateSetListener(object : OnDateSelectedListener {
                    override fun onClick(date: HijriObj) {
                        Toast.makeText(this@MainActivity, date.gregDate, Toast.LENGTH_SHORT).show()
                    }
                }).setYearRange(1441, 1450).setHijriMonthArray(hijriMonth)
                .show(supportFragmentManager, "DIALOG")
        })
    }

}