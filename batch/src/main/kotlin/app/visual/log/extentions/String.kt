package app.visual.log.extentions

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

fun String.dateConvert(): Date {
    val monthMap = HashMap<Long, String>()
    monthMap.put(1L, "Jan")
    monthMap.put(2L, "Feb")
    monthMap.put(3L, "Mar")
    monthMap.put(4L, "Apr")
    monthMap.put(5L, "May")
    monthMap.put(6L, "Jun")
    monthMap.put(7L, "Jul")
    monthMap.put(8L, "Aug")
    monthMap.put(9L, "Sep")
    monthMap.put(10L, "Oct")
    monthMap.put(11L, "Nov")
    monthMap.put(12L, "Dec")

    val formatterBuilder = DateTimeFormatterBuilder()
    formatterBuilder.appendValue(ChronoField.DAY_OF_MONTH)
    formatterBuilder.appendLiteral("/")
    formatterBuilder.appendText(ChronoField.MONTH_OF_YEAR, monthMap)
    formatterBuilder.appendLiteral("/")
    formatterBuilder.appendValue(ChronoField.YEAR)
    formatterBuilder.appendLiteral(":")
    formatterBuilder.appendValue(ChronoField.HOUR_OF_DAY)
    formatterBuilder.appendLiteral(":")
    formatterBuilder.appendValue(ChronoField.MINUTE_OF_HOUR)
    formatterBuilder.appendLiteral(":")
    formatterBuilder.appendValue(ChronoField.SECOND_OF_MINUTE)
    formatterBuilder.appendLiteral(" ")
    formatterBuilder.appendOffset("+HHmm", "UTC")
    val formatter = formatterBuilder.toFormatter()

    return Date.from(ZonedDateTime.from(formatter.parse(this)).toInstant())
}