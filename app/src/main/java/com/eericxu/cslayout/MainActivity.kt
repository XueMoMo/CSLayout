package com.eericxu.cslayout

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.eericxu.cslayout.databinding.ActivityMainBinding


class MainActivity : BindBaseAty<ActivityMainBinding>() {
    override fun inflate() = ActivityMainBinding.inflate(layoutInflater)
    val adapter = CardAdapter()
    val manager = GridLayoutManager(this, 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 < 1) 2 else 1
            }
        }
        bind.mList.layoutManager = manager
        bind.mList.adapter = adapter
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
