package com.katysh.cryptocompare.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoResponse (
    @SerializedName("Data")
    @Expose
    val data: List<Datum>?=null

)