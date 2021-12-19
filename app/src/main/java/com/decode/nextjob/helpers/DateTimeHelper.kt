package com.decode.nextjob.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateTimeHelper {

    @RequiresApi(Build.VERSION_CODES.O)
   public fun getDateTime(s: String): String? {
        val date: LocalDate = LocalDate.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        return  date.toString()
    }
}