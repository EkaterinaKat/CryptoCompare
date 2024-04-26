package com.katysh.cryptocompare.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katysh.cryptocompare.R
import com.katysh.cryptocompare.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!intent.hasExtra(FROM_SYM_EXTRA)) {
            finish()
            return
        }

        val fSym = intent.getStringExtra(FROM_SYM_EXTRA) ?: EMPTY_SYMBOL
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fSym))
                .commit()
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