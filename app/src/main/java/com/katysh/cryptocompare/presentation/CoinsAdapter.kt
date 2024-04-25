package com.katysh.cryptocompare.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.katysh.cryptocompare.R
import com.katysh.cryptocompare.databinding.PriceItemBinding
import com.katysh.cryptocompare.domain.entity.CoinInfo
import com.squareup.picasso.Picasso

class CoinsAdapter(private val context: Context) : Adapter<CoinsAdapter.CoinViewHolder>() {
    var coinInfoList: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            PriceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val priceInfo = coinInfoList[position]
        with(holder.binding) {
            with(priceInfo) {
                tvPrice.text = price
                tvSymbols.text = context.getString(R.string.syms, fromSymbol, toSymbol)
                tvLastUpdate.text =
                    context.getString(R.string.last_update_time, lastUpdate)
                Picasso.get().load(imageUrl).into(imageView)
                root.setOnClickListener {
                    onCoinClickListener?.execute(this)
                }
            }
        }
    }

    inner class CoinViewHolder(val binding: PriceItemBinding) : ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun execute(coinInfo: CoinInfo)
    }
}