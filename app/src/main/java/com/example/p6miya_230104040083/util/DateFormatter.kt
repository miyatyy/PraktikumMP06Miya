package id.antasari.p6miya_230104040083.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val fmt = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    fun format(ts: Long) = fmt.format(Date(ts))
}
