package com.naveenapps.expensemanager.core.designsystem.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.common.utils.fromLocalToUTCTimeStamp
import com.naveenapps.expensemanager.core.common.utils.toEpochMilliseconds
import com.naveenapps.expensemanager.core.common.utils.toExactStartOfTheDay
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.cancel
import expensemanager.core.designsystem4mp.generated.resources.select
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun AppDatePickerDialog(
    selectedDate: LocalDateTime,
    onDateSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochMilliseconds().fromLocalToUTCTimeStamp()
    )

    Dialog(
        onDismissRequest = {
            onDismiss.invoke()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                DatePicker(state = datePickerState)
                Row(
                    modifier = Modifier.align(Alignment.End),
                ) {
                    TextButton(onClick = {
                        onDismiss.invoke()
                    }) {
                        Text(text = stringResource(resource = Res.string.cancel).uppercase())
                    }
                    TextButton(onClick = {
                        onDateSelected.invoke(
                            datePickerState.selectedDateMillis?.toExactStartOfTheDay()
                                ?: Clock.System.now().asCurrentDateTime()
                        )
                    }) {
                        Text(text = stringResource(resource = Res.string.select).uppercase())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun AppDatePickerDialogPreview() {
    ExpenseManagerTheme {
        AppDatePickerDialog(
            selectedDate = Clock.System.now().asCurrentDateTime(),
            onDateSelected = {},
        ) {}
    }
}
