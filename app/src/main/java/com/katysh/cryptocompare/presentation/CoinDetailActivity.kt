package com.katysh.cryptocompare.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.cryptocompare.R

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val textView = findViewById<TextView>(R.id.tvDetail)

        if (!intent.hasExtra(FROM_SYM_EXTRA)) {
            finish()
            return
        }

        val fSym = intent.getStringExtra(FROM_SYM_EXTRA) ?: EMPTY_SYMBOL
        viewModel.getDetailInfo(fSym).observe(this) { textView.text = it.toString() }

//        viewModel.getDetailInfo(fromSymbol).observe(this) {
//            tvPrice.text = it.price
//            tvMinPrice.text = it.lowDay
//            tvMaxPrice.text = it.highDay
//            tvLastMarket.text = it.lastMarket
//            tvLastUpdate.text = convertTimestampToTime(it.lastUpdate)
//            tvFromSymbol.text = it.fromSymbol
//            tvToSymbol.text = it.toSymbol
//            Picasso.get().load(it.imageUrl).into(ivLogoCoin)
//        }
    }

    companion object{
        private const val FROM_SYM_EXTRA = "from sym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fSym: String):Intent{
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FROM_SYM_EXTRA, fSym)
            return intent
        }
    }
}