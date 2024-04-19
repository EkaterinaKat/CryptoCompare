package com.katysh.cryptocompare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.katysh.cryptocompare.pojo.PriceInfo

class PriceListActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.rvPriceList)
        val adapter = CoinsAdapter(this)
        adapter.onCoinClickListener = object : CoinsAdapter.OnCoinClickListener {
            override fun execute(priceInfo: PriceInfo) {
                startActivity(
                    CoinDetailActivity.newIntent(this@PriceListActivity, priceInfo.fromSymbol)
                )
            }
        }
        recyclerView.adapter = adapter

        viewModel.priceList.observe(this) {
            adapter.priceInfoList = it
        }
    }

}