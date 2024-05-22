package ir.dorantech.kmmreference

import android.annotation.SuppressLint
import android.icu.util.Calendar

val cal = Calendar.getInstance()

@SuppressLint("DefaultLocale")
fun getTransactionDateAndroid(): String {
    return String.format(
        "%02d/%02d/%04d",
        cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.DAY_OF_MONTH),
        cal.get(Calendar.YEAR)
    )
}

@SuppressLint("DefaultLocale")
fun getTransactionTimeAndroid(): String {
    return String.format(
        "%02d:%02d:%02d",
        cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE),
        cal.get(Calendar.SECOND)
    )
}