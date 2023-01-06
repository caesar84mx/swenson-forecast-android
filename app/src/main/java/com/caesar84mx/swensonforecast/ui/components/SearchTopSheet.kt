@file:OptIn(ExperimentalMaterialApi::class)

package com.caesar84mx.swensonforecast.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.DismissValue.DismissedToStart
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caesar84mx.swensonforecast.R
import com.caesar84mx.swensonforecast.R.drawable
import com.caesar84mx.swensonforecast.ui.main_screen.data.LocationUI
import com.caesar84mx.swensonforecast.ui.theme.Accent
import com.caesar84mx.swensonforecast.ui.theme.HintColor
import com.caesar84mx.swensonforecast.ui.theme.OnSurface
import com.caesar84mx.swensonforecast.ui.theme.Theme

@Composable
fun SearchTopSheet(
    label: String,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    options: List<LocationUI>,
    onOptionPressed: (LocationUI) -> Unit,
    onErasePressed: () -> Unit,
    onBackPressed: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val drawerShape = RoundedCornerShape(
        bottomStartPercent = 13,
        bottomEndPercent = 13,
    )
    val fieldShape = RoundedCornerShape(30)

    Surface(
        elevation = 10.dp,
        modifier = Modifier
            .clip(drawerShape)
            .fillMaxWidth()
    ) {
        Column {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(vertical = 27.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            painter = painterResource(id = drawable.ic_arrow_back),
                            contentDescription = "back",
                            tint = OnSurface
                        )
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChanged,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                            keyboardType = KeyboardType.Text,
                            autoCorrect = false
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = { focusManager.clearFocus() }
                        ),
                        shape = fieldShape,
                        label = {
                            Text(text = label)
                        },
                        trailingIcon = {
                            IconButton(onClick = onErasePressed) {
                                Icon(
                                    painter = painterResource(id = drawable.ic_close),
                                    contentDescription = "close",
                                    tint = OnSurface
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = OnSurface,
                            unfocusedBorderColor = OnSurface,
                            focusedLabelColor = HintColor,
                            unfocusedLabelColor = HintColor,
                            trailingIconColor = OnSurface,
                            cursorColor = OnSurface,
                            textColor = OnSurface
                        )
                    )
                }

                LazyColumn {
                    items(options) { option ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                focusManager.clearFocus()
                                onOptionPressed(option)
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {

                                Text(
                                    text = "${option.city} - ${option.state}",
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (options.isNotEmpty()) {


                val bottomSize = 38.dp
                val sizePx = with(LocalDensity.current) { -(bottomSize).toPx() }
                val anchors = mapOf(0f to 0, sizePx to 1)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(bottomSize)
                        .background(Accent)
                        .fillMaxWidth()
                        .swipeable(
                            state = rememberSwipeableState(0) {
                                if (it == 1) {
                                    onBackPressed()
                                }

                                true
                            },
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Vertical,
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chevron_up),
                        contentDescription = null,
                        tint = OnSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTopSheetPreview() {
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .background(Theme)
            .fillMaxSize()
    ) {
        SearchTopSheet(
            label = "Search City",
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
            options = listOf(),
            onOptionPressed = {},
            onErasePressed = {},
            onBackPressed = {}
        )
    }
}