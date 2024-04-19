package com.katysh.cryptocompare

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val textView = findViewById<TextView>(R.id.tvDetail)

        if(!intent.hasExtra(FROM_SYM_EXTRA)){
            finish()
            return
        }

        val fSym = intent.getStringExtra(FROM_SYM_EXTRA)
        fSym?.let {
            viewModel.getDetailInfo(fSym).observe(this){
                textView.text = it.toString()
            }
        }
    }

    companion object{
        private const val FROM_SYM_EXTRA = "from sym"

        fun newIntent(context: Context, fSym: String):Intent{
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FROM_SYM_EXTRA, fSym)
            return intent
        }
    }
}