package com.example.fazyksiezyca

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_full_moon.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.util.*

class FullMoonActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moon)

        var etYear = findViewById<EditText>(R.id.etYear)
        var btnSearch = findViewById<Button>(R.id.saveButton)

        btnSearch.setOnClickListener() {
            val yearValue = etYear.text
            if (yearValue.toString().toInt() in 1900..2200) {

                val array = arrayOf(date1, date2, date3, date4, date5, date6,
                    date7, date8, date9, date10, date11, date12, date13)

                date12.text = ""
                date13.text = ""

                val set = readFile()
                val algorithm = set.algorithm

                var tempDate: LocalDate
                var days: Int
                var i: Int = 0
                for (d in 0..365) {
                    tempDate = LocalDate.of(yearValue.toString().toInt(), 1, 1)
                        .plusDays(d.toLong())
                    days = if (algorithm == "Simple") Simple(tempDate.year, tempDate.monthValue, tempDate.dayOfMonth)
                    else if (algorithm == "Convey") Conway(tempDate.year, tempDate.monthValue, tempDate.dayOfMonth)
                    else if (algorithm == "Trig1") Trig1(tempDate.year, tempDate.monthValue, tempDate.dayOfMonth)
                    else Trig2(tempDate.year, tempDate.monthValue, tempDate.dayOfMonth)
                    if (days == 15 && i < 13) {
                        array[i].text = tempDate.toString()
                        i++
                    }
                }

            } else
                Toast.makeText(this, "Proszę podać rok z przedziału 1900-2200",
                    Toast.LENGTH_SHORT).show()

        }
    }

    private fun FileExists(path: String): Boolean {
        val file = baseContext.getFileStreamPath(path)
        return file.exists()
    }

    fun readFile(): Settings {
        val settings = ArrayList<Settings>()
        try {
            val filename = "settings.csv"
            if (FileExists(filename)) {
                val file = InputStreamReader(openFileInput(filename))
                val br = BufferedReader(file)

                var line = br.readLine()
                while (line != null) {
                    settings.add(Settings(line))
                    line = br.readLine()
                }
                file.close()
                Toast.makeText(this, "${settings[0].algorithm}, ${settings[0].hemiSphere}", Toast.LENGTH_LONG).show()
                return settings[0]
            } else {
                Toast.makeText(this, "Błąd w ustawieniach. Wybierz algorytm i półkulę", Toast.LENGTH_LONG).show()
                return Settings("północna", "Trig1")
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Błąd", Toast.LENGTH_LONG).show()
            return Settings("północna", "Trig1")
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}
