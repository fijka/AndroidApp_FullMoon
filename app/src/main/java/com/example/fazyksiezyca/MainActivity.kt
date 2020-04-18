package com.example.fazyksiezyca

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val set = readFile()

        val currentDay = LocalDateTime.now().dayOfMonth
        val currentMonth = LocalDateTime.now().monthValue
        val currentYear = LocalDateTime.now().year

        val hemisphere: String
        val algorithm = set.algorithm
        if (set.hemiSphere == "północna") hemisphere = "n"
        else hemisphere = "s"


        val today: Int
        today = if (algorithm == "Simple") Simple(currentYear, currentMonth, currentDay)
        else if (algorithm == "Convey") Conway(currentYear, currentMonth, currentDay)
        else if (algorithm == "Trig1") Trig1(currentYear, currentMonth, currentDay)
        else Trig2(currentYear, currentMonth, currentDay)

        val percent = (today / 30.0 * 100.0).roundToInt()

        val last = LocalDateTime.now().minusDays((today).toLong())
        val next = LocalDateTime.now().plusDays((30-today+15).toLong())

        val fileN = "$hemisphere$today.jpg"

        val img: Int = displayImage(fileN)

        imageView.setImageResource(img)
        dzisiaj.text = "Dzisiaj: $percent%"
        lastNewMoon.text = "Poprzedni nów: ${last.dayOfMonth}.${last.monthValue}.${last.year} r."
        nextFullMoon.text = "Następna pełnia: ${next.dayOfMonth}.${next.monthValue}.${next.year} r."

        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        val fullButton = findViewById<Button>(R.id.fullButton)
        fullButton.setOnClickListener {
            val intent = Intent(this, FullMoonActivity::class.java)
            startActivity(intent)
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

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

}
