package com.katysh.cryptocompare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.katysh.cryptocompare.pojo.PriceInfo
import com.squareup.picasso.Picasso

class CoinsAdapter(private val context: Context) : Adapter<CoinsAdapter.CoinViewHolder>() {
    var priceInfoList: List<PriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.price_item, parent, false
            )
        )
    }

    override fun getItemCount() = priceInfoList.size

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val priceInfo = priceInfoList[position]
        with(holder) {
            with(priceInfo) {
                tvPrice.text = price
                tvSymbols.text = context.getString(R.string.syms, fromSymbol, toSymbol)
                tvLastUpdate.text = context.getString(R.string.last_update_time, getFormattedTime())
                Picasso.get().load(getFullImageUrl()).into(imageView)
                itemView.setOnClickListener {
                    onCoinClickListener?.execute(this)
                }
            }
        }
    }

    inner class CoinViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvSymbols = itemView.findViewById<TextView>(R.id.tvSymbols)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvLastUpdate = itemView.findViewById<TextView>(R.id.tvLastUpdate)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }

    interface OnCoinClickListener {
        fun execute(priceInfo: PriceInfo)
    }
}