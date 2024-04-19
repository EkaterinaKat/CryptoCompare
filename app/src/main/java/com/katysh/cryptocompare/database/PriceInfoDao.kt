package com.katysh.cryptocompare.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katysh.cryptocompare.pojo.PriceInfo

@Dao
interface PriceInfoDao {

    @Query("SELECT * FROM price_info ORDER BY lastUpdate DESC")
    fun getPriceList():LiveData<List<PriceInfo>>

    @Query("SELECT * FROM price_info WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym:String):LiveData<PriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList:List<PriceInfo>)
}