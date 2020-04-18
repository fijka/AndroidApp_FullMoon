package com.example.fazyksiezyca

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.OutputStreamWriter

class SettingsActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val algList = resources.getStringArray(R.array.algorithms)
        if (algSpinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                algList)
            algSpinner.adapter = adapter
        }

        radioGroup = findViewById(R.id.radioGroup)
        saveBtn = findViewById(R.id.saveButton)

        saveBtn.setOnClickListener {
            val value = radioGroup.checkedRadioButtonId
            val rb = findViewById<RadioButton>(value)
            val value2: String = algSpinner.selectedItem.toString()

            val settings = Settings("${rb.text}", value2)

            val filename = "settings.csv"
            val file = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))

            file.write(settings.toCSV())
            file.flush()
            file.close()

            Toast.makeText(this, "Ustawienia zostały zapisane", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }

}
