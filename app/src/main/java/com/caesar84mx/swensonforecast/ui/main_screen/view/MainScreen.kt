@file:OptIn(ExperimentalAnimationApi::class)

package com.caesar84mx.swensonforecast.ui.main_screen.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.caesar84mx.swensonforecast.R
import com.caesar84mx.swensonforecast.util.providers.GlobalEventsProvider
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Error
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Idle
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Loading
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Success
import com.caesar84mx.swensonforecast.data.networking.repository.ForecastRepository
import com.caesar84mx.swensonforecast.ui.components.ClockView
import com.caesar84mx.swensonforecast.ui.components.LifecycleDisposableObserver
import com.caesar84mx.swensonforecast.ui.components.SearchTopSheet
import com.caesar84mx.swensonforecast.ui.components.SlideAnimatedView
import com.caesar84mx.swensonforecast.ui.main_screen.data.DaysForecastUI
import com.caesar84mx.swensonforecast.ui.main_screen.viewmodel.MainScreenViewModel
import com.caesar84mx.swensonforecast.ui.theme.SwensonForecastTheme
import com.caesar84mx.swensonforecast.util.providers.LocationProvider
import com.caesar84mx.swensonforecast.util.providers.ResourceProvider
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = getViewModel()

    LifecycleDisposableObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onBackPressed = viewModel::onBackPressed
    )

    Body(viewModel = viewModel)
}

@Composable
private fun Body(viewModel: MainScreenViewModel) {
    val status by viewModel.status.collectAsState()
    val showSearch by viewModel.showSearch.collectAsState()
    val locations by viewModel.locations.collectAsState()
    val citySearchQuery by viewModel.citySearchQuery.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.mipmap.bkg_tram),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets(0.dp))
                .fillMaxSize()
        )

        Box(
            modifier = Modifier
                .background(Color(0xD4002762))
                .windowInsetsPadding(WindowInsets(0.dp))
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 32.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(104.dp),
            ) {
                Spacer(Modifier.weight(1.5f))

                ClockView(
                    spSize = MaterialTheme.typography.body1.fontSize.value,
                    color = Color.White
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = viewModel::onSearchPressed) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(R.string.ic_description_search)
                    )
                }
            }

            when(status) {
                Loading -> {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is Success<*> -> {
                    val forecast = (status as Success<DaysForecastUI>).data
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(bottom = 101.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = forecast.current.location,
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = forecast.current.date,
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(horizontal = 80.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(forecast.current.iconUrl)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop,
                                contentDescription = forecast.current.description,
                                modifier = Modifier
                                    .size(70.dp)
                                    .scale(2f)
                            )

                            Text(
                                text = forecast.current.temperature,
                                style = MaterialTheme.typography.h2,
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(
                                    top = 23.dp,
                                    bottom = 18.dp
                                )
                            )

                            Text(
                                text = forecast.current.description,
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 21.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 9.dp)
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_wind),
                                    contentDescription = null
                                )

                                Text(
                                    text = forecast.current.windSpeed,
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.padding(start = 9.dp)
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    painter = painterResource(id = R.drawable.ic_droplet),
                                    contentDescription = null
                                )

                                Text(
                                    text = forecast.current.humidity,
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.padding(start = 9.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .height(190.dp)
                    ) {
                        forecast.forecast.forEach { daily ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding()
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(daily.dailyForecast.iconUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )

                                Text(
                                    text = daily.dailyForecast.temperatureRange,
                                    style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W700),
                                    color = MaterialTheme.colors.onBackground
                                )

                                Text(
                                    text = daily.day,
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground
                                )
                            }
                        }
                    }
                }
                is Error -> {
                    val status = status as Error
                    AlertDialog(
                        title = { Text(text = stringResource(R.string.error_title)) },
                        text = { Text(text = status.message) },
                        buttons = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(onClick = viewModel::onErrorMessageClose) {
                                    Text(
                                        text = stringResource(R.string.btn_ok),
                                        style = MaterialTheme.typography.button
                                    )
                                }
                            }
                        },
                        onDismissRequest = viewModel::onErrorMessageClose
                    )
                }
                Idle -> {/* no-op */}
            }
        }

        SlideAnimatedView(targetState = showSearch) { isShown ->
            if (isShown) {
                SearchTopSheet(
                    label = "Search City",
                    searchQuery = citySearchQuery,
                    onSearchQueryChanged = viewModel::onCityQueryChanged,
                    options = locations,
                    onOptionPressed = viewModel::onLocationPressed,
                    onErasePressed = viewModel::onErasePressed,
                    onBackPressed = viewModel::onSearchDismiss
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    val context = LocalContext.current
    SwensonForecastTheme {
        Body(
            viewModel = MainScreenViewModel(
                locationProvider = LocationProvider.mock,
                resourceProvider = ResourceProvider(context),
                repo = ForecastRepository.mock,
                globalEventsProvider = GlobalEventsProvider()
            )
        )
    }
}