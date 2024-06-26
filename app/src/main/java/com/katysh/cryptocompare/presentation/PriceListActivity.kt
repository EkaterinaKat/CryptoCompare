package com.katysh.cryptocompare.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.cryptocompare.CryptoApplication
import com.katysh.cryptocompare.R
import com.katysh.cryptocompare.databinding.ActivityPriceListBinding
import com.katysh.cryptocompare.domain.entity.CoinInfo
import javax.inject.Inject

class PriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as CryptoApplication).component
    }

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

        val adapter = CoinsAdapter(this)
        adapter.onCoinClickListener = object : CoinsAdapter.OnCoinClickListener {
            override fun execute(coinInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinInfo.fromSymbol)
                }
            }
        }
        binding.rvPriceList.adapter = adapter

        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailActivity(fSym: String) {
        startActivity(CoinDetailActivity.newIntent(this@PriceListActivity, fSym))
    }

    private fun launchDetailFragment(fSym: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fSym))
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null
}