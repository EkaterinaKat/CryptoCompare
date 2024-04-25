package com.katysh.cryptocompare.presentation

import androidx.recyclerview.widget.DiffUtil
import com.katysh.cryptocompare.domain.entity.CoinInfo

object CoinDiffCallback : DiffUtil.ItemCallback<CoinInfo>() {

    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}