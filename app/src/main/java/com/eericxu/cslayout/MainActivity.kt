package com.eericxu.cslayout

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAty() {
    val adapter = CardAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mList.layoutManager = LinearLayoutManager(this)
        mList.adapter = adapter
        lifecycle.addObserver(object :LifeObserver(){
            override fun onResume() {
                adapter.onResume()
            }

            override fun onPause() {
                adapter.onPause()
            }
        })
    }
}
