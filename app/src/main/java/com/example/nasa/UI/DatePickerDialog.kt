package com.example.nasa.UI

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.util.*

private const val TAG = "DatePickerDialog"
class DatePickerDialog(private val listener : DatePickerDialog.OnDateSetListener) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val date = c.get(Calendar.DATE)
                Log.e(TAG,"$date/$month/$year")
                return DatePickerDialog(requireContext(),listener,year,month,date)
    }
}