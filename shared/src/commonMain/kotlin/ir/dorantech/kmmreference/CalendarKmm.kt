package ir.dorantech.kmmreference

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val currentMoment = Clock.System.now()
val currentLocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
fun getTransactionDateKmm(): String = "${currentLocalDateTime.date.monthNumber}/${currentLocalDateTime.date.dayOfMonth}/${currentLocalDateTime.date.year}"
fun getTransactionTimeKmm(): String = "${currentLocalDateTime.time.hour}:${currentLocalDateTime.time.minute}:${currentLocalDateTime.time.second}"