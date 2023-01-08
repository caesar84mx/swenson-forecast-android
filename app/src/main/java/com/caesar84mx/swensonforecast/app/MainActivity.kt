package com.caesar84mx.swensonforecast.app

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.caesar84mx.swensonforecast.R
import com.caesar84mx.swensonforecast.data.model.common.QuitApp
import com.caesar84mx.swensonforecast.ui.main_screen.view.MainScreen
import com.caesar84mx.swensonforecast.ui.theme.SwensonForecastTheme
import com.caesar84mx.swensonforecast.util.providers.GlobalEventsProvider
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : FragmentActivity(), KoinComponent {
    private val eventsProvider: GlobalEventsProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        listenEvents()

        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    setContent {
                        SwensonForecastTheme {
                            MainScreen()
                        }
                    }
                }
            }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun listenEvents() {
        eventsProvider.globalEvent
            .onEach { event ->
                when(event) {
                    QuitApp -> finish()
                }
            }.launchIn(lifecycleScope)
    }
}