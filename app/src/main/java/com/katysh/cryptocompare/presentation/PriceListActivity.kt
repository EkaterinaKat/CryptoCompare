package com.katysh.cryptocompare.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.cryptocompare.databinding.ActivityPriceListBinding
import com.katysh.cryptocompare.domain.entity.CoinInfo

class PriceListActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val adapter = CoinsAdapter(this)
        adapter.onCoinClickListener = object : CoinsAdapter.OnCoinClickListener {
            override fun execute(coinInfo: CoinInfo) {
                startActivity(
                    CoinDetailActivity.newIntent(this@PriceListActivity, coinInfo.fromSymbol)
                )
            }
        }
        binding.rvPriceList.adapter = adapter

        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }

}