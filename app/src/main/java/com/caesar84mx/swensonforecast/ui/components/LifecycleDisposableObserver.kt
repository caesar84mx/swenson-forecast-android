package com.caesar84mx.swensonforecast.ui.components

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleDisposableObserver(
    lifecycleOwner: LifecycleOwner,
    onCreate: DisposableEffectScope.() -> Unit = {},
    onStart: DisposableEffectScope.() -> Unit = {},
    onResume: DisposableEffectScope.() -> Unit = {},
    onPause: DisposableEffectScope.() -> Unit = {},
    onStop: DisposableEffectScope.() -> Unit = {},
    onDestroy: DisposableEffectScope.() -> Unit = {},
    onBackPressed: (() -> Unit)? = null
) {
    val currentOnBack by rememberUpdatedState {
        onBackPressed?.invoke()
    }

    val backCallback = onBackPressed?.let {
        remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentOnBack()
                }
            }
        }.apply { isEnabled = true }
    }

    val backDispatcher = onBackPressed?.let { LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher }

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backCallback?.let { callback -> backDispatcher?.addCallback(lifecycleOwner, callback) }

        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                ON_CREATE -> onCreate()
                ON_START -> onStart()
                ON_RESUME -> onResume()
                ON_PAUSE -> onPause()
                ON_STOP -> onStop()
                ON_DESTROY -> onDestroy()
                else -> { /* no-op */ }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            onBackPressed?.let { backCallback?.remove() }
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}