package com.katysh.cryptocompare.data.web.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameListDTO(
    @SerializedName("Data")
    @Expose
    val names: List<CoinNameContainerDTO>? = null

)