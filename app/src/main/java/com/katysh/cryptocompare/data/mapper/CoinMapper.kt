package com.katysh.cryptocompare.data.mapper

import com.google.gson.Gson
import com.katysh.cryptocompare.data.database.CoinInfoDBM
import com.katysh.cryptocompare.data.web.model.CoinInfoDTO
import com.katysh.cryptocompare.data.web.model.CoinInfoJsonContainerDTO
import com.katysh.cryptocompare.data.web.model.CoinNameListDTO
import com.katysh.cryptocompare.domain.entity.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CoinMapper {

    fun mapDtoToDbm(dto: CoinInfoDTO) = CoinInfoDBM(
        fromSymbol = dto.fromSymbol,
        price = dto.price,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        lastUpdate = dto.lastUpdate,
        toSymbol = dto.toSymbol,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToDtoList(json: CoinInfoJsonContainerDTO): List<CoinInfoDTO> {
        val result = mutableListOf<CoinInfoDTO>()
        val jsonObject = json.json ?: return result

        val coinKeySet = jsonObject.keySet() // [BTC,ETH...]
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet() // [USD]
            for (currencyKey in currencyKeySet) {
                val coinInfoDto = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),  //берём объект лежащий под ключём USD
                    CoinInfoDTO::class.java                       //и превращаем в PriceInfo
                )
                result.add(coinInfoDto)
            }
        }
        return result
    }

    fun mapCoinNameListToString(list: CoinNameListDTO): String {
        return list.names?.map { it.coinNameDTO?.name }?.joinToString(",") ?: ""
    }

    fun mapDbmToEntity(dbm: CoinInfoDBM) = CoinInfo(
        fromSymbol = dbm.fromSymbol,
        price = dbm.price,
        highDay = dbm.highDay,
        lowDay = dbm.lowDay,
        lastMarket = dbm.lastMarket,
        lastUpdate = convertTimeStampToTime(dbm.lastUpdate),
        toSymbol = dbm.toSymbol,
        imageUrl = dbm.imageUrl
    )

    private fun convertTimeStampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}