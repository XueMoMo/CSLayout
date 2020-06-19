package com.eericxu.cslayout

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.Event.*
import android.util.Log

/**
 * Created by Eericxu on 2018-04-11.
 *
 * 生命周期回调
 */
@SuppressLint("RestrictedApi")
open class LifeObserver : LifecycleEventObserver {
    open fun onCreate() {}

    open fun onStart() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

    open fun onDestroy() {}

    open fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {}

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.i("Eericxu","LifeObserver:${event.name}")
        onAny(source, event)
        if (event == ON_DESTROY)
            source.lifecycle.removeObserver(this)
        when (event) {
            ON_START -> onStart()
            ON_CREATE -> onCreate()
            ON_RESUME -> onResume()
            ON_PAUSE -> onPause()
            ON_STOP -> onStop()
            ON_DESTROY -> onDestroy()
            else -> {
            }
        }
    }
}