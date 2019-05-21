package pl.michalgellert.trendingrepos.util

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateCalculatorTest {

    @Test
    fun getCreated30DaysAgo_startWithCreated_returnsTrue() {
        assert(DateCalculator().getCreatedDate30DaysAgo().startsWith("created:>"))
    }

    @Test
    fun getCreated30DaysAgo_isCorrectFormat_returnsTrue() {
        val date = DateCalculator().getCreatedDate30DaysAgo().substring(9)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        assert(sdf.parse(date) is Date)
    }

    @Test
    fun getCreated30DaysAgo_is30DaysAgo_returnsTrue() {
        val date = DateCalculator().getCreatedDate30DaysAgo().substring(9)
        val cal = Calendar.getInstance()
        val today = Calendar.getInstance()
        today.add(Calendar.DATE, -30)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        cal.time = sdf.parse(date)
        assert(
            cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    cal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
        )
    }
}