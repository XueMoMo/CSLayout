package com.eericxu.cslayout

import android.arch.lifecycle.*
import android.arch.lifecycle.Lifecycle.Event.*

/**
 * Created by Eericxu on 2018-04-11.
 *
 * 生命周期回调
 */
open class LifeObserver : GenericLifecycleObserver {
    override fun onStateChanged(source: LifecycleOwner?, event: Lifecycle.Event?) {
//        Log.i("LifeObserver:${event?.name}")
        if (source != null && event != null)
            onAny(source, event)
        if (event != null && event == Lifecycle.Event.ON_DESTROY)
            source?.lifecycle?.removeObserver(this)
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

    open fun onCreate() {}

    open fun onStart() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

    open fun onDestroy() {}

    open fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {}
}