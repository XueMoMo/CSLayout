package com.eericxu.cslayout

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAty() {
    val adapter = CardAdapter()
    val manager = GridLayoutManager(this, 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 < 1) 2 else 1
            }
        }
        mList.layoutManager = manager
        mList.adapter = adapter
        lifecycle.addObserver(object : LifeObserver() {
            override fun onResume() {
                adapter.onResume()
            }

            override fun onPause() {
                adapter.onPause()
            }
        })
    }
}
