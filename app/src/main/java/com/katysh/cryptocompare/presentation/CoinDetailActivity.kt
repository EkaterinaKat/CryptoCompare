package com.katysh.cryptocompare.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.cryptocompare.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        if (!intent.hasExtra(FROM_SYM_EXTRA)) {
            finish()
            return
        }

        val fSym = intent.getStringExtra(FROM_SYM_EXTRA) ?: EMPTY_SYMBOL



        viewModel.getDetailInfo(fSym).observe(this) {
            with(binding) {
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
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