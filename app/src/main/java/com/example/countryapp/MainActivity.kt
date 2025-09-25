package com.example.countryapp

import DialogExit
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.countryapp.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var provinces: Array<String>
    val countries = arrayOf(
        "Indonesia",
        "United States",
        "United Kingdom",
        "Germany",
        "France",
        "Australia",
        "Japan",
        "China",
        "Brazil",
        "Canada"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        provinces = resources.getStringArray(R.array.provinces) //Pastikan sudah menambahkan Array resources pada "strings.xml"

        with(binding) {
            val adapterCountry = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_item, countries
            )
            adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountry.adapter = adapterCountry

            val adapterProvinces = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_item, provinces
            )
            adapterProvinces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProvinces.adapter = adapterProvinces

            spinnerCountry.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        Toast.makeText(
                            this@MainActivity,
                            countries[position], Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action ex: Peringatan kepada user bahwa belum memilih dropdown item
                    }
                }

            datePicker.init(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth
            ) { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                Toast.makeText(this@MainActivity, selectedDate, Toast.LENGTH_SHORT).show()
                // Gunakan selectedDate sesuai kebutuhan Anda
            }

            timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                Toast.makeText(this@MainActivity, selectedTime, Toast.LENGTH_SHORT).show()
            // Gunakan selectedTime sesuai kebutuhan Anda
            }

            class DatePicker: DialogFragment() {
                override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val monthOfYear = calendar.get(Calendar.MONTH)
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                    return DatePickerDialog(
                        requireActivity(),
                        activity as DatePickerDialog.OnDateSetListener,
                        year,
                        monthOfYear,
                        dayOfMonth
                    )
                }
            }

            btnShowCalendar.setOnClickListener {
                val datePicker = DatePicker()
                datePicker.show(supportFragmentManager, "datePicker")
            }

            class TimePicker: DialogFragment() {
                override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                    val calendar = Calendar.getInstance()
                    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)
                    return TimePickerDialog(
                        requireActivity(),
                        activity as TimePickerDialog.OnTimeSetListener,
                        hourOfDay,
                        minute,
                        DateFormat.is24HourFormat(activity) // alt + enter lalu import android.text.format.DateFormat
                    )
                }
            }
            btnShowTimePicker.setOnClickListener {
                val timePicker = TimePicker()
                timePicker.show(supportFragmentManager, "timePicker")
            }

            btnShowAlertDialog.setOnClickListener {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Keluar")
                builder.setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
                builder.setPositiveButton("Ya") { dialog, which ->
                //lakukan sesuatu ketika tombol positif diklik
                    finish()
                }
                builder.setNegativeButton("Tidak") { dialog, _ ->
                //lakukan sesuatu ketika tombol negatif diklik
                    dialog.dismiss()
                }
                // Membuat dan menampilkan dialog
                val dialog = builder.create()
                dialog.show()
            }

            btnShowCustomDialog.setOnClickListener {
                val dialog = DialogExit()
                dialog.show(supportFragmentManager, "dialogExit")
            }
        }
    }

    // Hasil implementasi dari interface "DatePickerDialog.OnDateSetListener" pada class main activity
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        // Gunakan p1, p2, p3 untuk mendapatkan tanggal, bulan, dan tahun
        val selectedDate = "$p3/${p2 + 1}/$p1"
        // Toast berfungsi untuk menampilkan pop-up pesan singkat, dalam hal ini akan menampilkan tanggal yang dipilih
        Toast.makeText(this@MainActivity, selectedDate,Toast.LENGTH_SHORT).show()
    }

    // Sama halnya dengan onTimeSet, namun untuk TimePicker
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        // Gunakan p1 dan p2 untuk mendapatkan jam dan menit yang dipilih
        val selectedTime = String.format("%02d:%02d", p1, p2)
        // Toast berfungsi untuk menampilkan pop-up pe san singkat, dalam hal ini akan menampilkan jam dan menit yang dipilih
        Toast.makeText(this@MainActivity, selectedTime,Toast.LENGTH_SHORT).show()
    }
}