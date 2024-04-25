package com.katysh.cryptocompare.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.katysh.cryptocompare.R
import com.katysh.cryptocompare.domain.entity.CoinInfo

class PriceListActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.rvPriceList)
        val adapter = CoinsAdapter(this)
        adapter.onCoinClickListener = object : CoinsAdapter.OnCoinClickListener {
            override fun execute(coinInfo: CoinInfo) {
                startActivity(
                    CoinDetailActivity.newIntent(this@PriceListActivity, coinInfo.fromSymbol)
                )
            }
        }
        recyclerView.adapter = adapter

        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }

}