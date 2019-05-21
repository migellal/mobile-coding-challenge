package pl.michalgellert.trendingrepos.util

import java.text.SimpleDateFormat
import java.util.*

class DateCalculator {
    fun getCreatedDate30DaysAgo(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -30)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return "created:>${dateFormat.format(calendar.time)}"
    }
}