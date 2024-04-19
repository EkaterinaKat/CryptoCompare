package com.katysh.cryptocompare.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertTimeStampToTime(timestamp: Long?):String{
    if(timestamp==null) return ""
    val stamp = Timestamp(timestamp*1000)
    val date = Date(stamp.time)
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}