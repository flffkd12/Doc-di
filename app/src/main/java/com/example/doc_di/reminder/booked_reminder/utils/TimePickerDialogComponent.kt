package com.example.doc_di.reminder.booked_reminder.utils

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.doc_di.R
import com.example.doc_di.reminder.medication_reminder.model.CalendarInformation
import java.util.Calendar

@Composable
fun TimePickerDialogComponent(
    showDialog: Boolean,
    selectedDate: CalendarInformation,
    onSelectedTime: (selectedDate: CalendarInformation) -> Unit
) {
    val listener = setUpOnTimeSetListener(onSelectedTime)
    val timePickerDialog = getTimePickerDialog(selectedDate, listener)
    if (showDialog) {
        timePickerDialog.show()
    }
}

private fun setUpOnTimeSetListener(
    onSelectedTime: (selectedDate: CalendarInformation) -> Unit
): TimePickerDialog.OnTimeSetListener {
    return TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
        val newDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        onSelectedTime(CalendarInformation(newDate))
    }
}

@Composable
private fun getTimePickerDialog(
    selectedDate: CalendarInformation,
    listener: TimePickerDialog.OnTimeSetListener
): TimePickerDialog {
    val context = LocalContext.current
    val (hour, minute) = selectedDate.dateInformation
    return TimePickerDialog(
        context,
        R.style.CustomTimePickerTheme,
        listener,
        hour,
        minute,
        false
    )
}