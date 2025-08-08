package com.naveenapps.expensemanager.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naveenapps.expensemanager.core.common.utils.toColorString
import com.naveenapps.expensemanager.core.designsystem.ui.components.RoundIconView
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.ui.utils.toColor
import com.naveenapps.expensemanager.core.designsystem.utils.Exports
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


enum class SelectionType {
    NONE,
    COLOR_SELECTION,
    ICON_SELECTION,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconAndColorComponent(
    selectedColor: String,
    selectedIcon: String,
    onColorSelection: ((String) -> Unit)?,
    onIconSelection: ((String) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            true
        },
    )

    val scope = rememberCoroutineScope()
    var sheetSelection by remember { mutableStateOf(SelectionType.NONE) }

    if (sheetSelection != SelectionType.NONE) {
        ModalBottomSheet(
            modifier = Modifier.wrapContentSize(),
            sheetState = sheetState,
            onDismissRequest = {
                sheetSelection = SelectionType.NONE
            },
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            when (sheetSelection) {
                SelectionType.COLOR_SELECTION -> {
                    ColorSelectionScreen {
                        onColorSelection?.invoke(it.toColorString())
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                sheetSelection = SelectionType.NONE
                            }
                        }
                    }
                }

                SelectionType.ICON_SELECTION -> {
                    IconSelectionScreen {
                        onIconSelection?.invoke(it)
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                sheetSelection = SelectionType.NONE
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }

    Row(modifier = modifier) {
        val containerColor = selectedColor.toColor()
        val contentColor = Color.White

        RoundIconView(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    sheetSelection = SelectionType.COLOR_SELECTION
                    focusManager.clearFocus(force = true)
                }
                .align(Alignment.CenterVertically),
            iconBackgroundColor = selectedColor,
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            text = "+",
            fontSize = 24.sp,
        )

        FilledTonalIconButton(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                sheetSelection = SelectionType.ICON_SELECTION
                focusManager.clearFocus(force = true)
            },
        ) {
            Icon(
                painter = painterResource(Exports.drawableBy(selectedIcon)),
                contentDescription = "",
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            text = "=",
            fontSize = 24.sp,
        )

        FilledTonalIconButton(
            modifier = Modifier.wrapContentSize(),
            onClick = { },
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        ) {
            Icon(
                painter = painterResource(Exports.drawableBy(selectedIcon)),
                contentDescription = "",
            )
        }
    }
}

@Composable
@Preview
private fun IconAndColorComponentPreview() {
    ExpenseManagerTheme {
        IconAndColorComponent(
            selectedColor = "#FF000000",
            selectedIcon = "ic_add",
            onColorSelection = {},
            onIconSelection = {},
        )
    }
}
