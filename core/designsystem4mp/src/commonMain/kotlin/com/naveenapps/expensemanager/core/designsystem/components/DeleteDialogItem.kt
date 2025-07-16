package com.naveenapps.expensemanager.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.delete
import expensemanager.core.designsystem4mp.generated.resources.delete_item_message
import expensemanager.core.designsystem4mp.generated.resources.no_keep
import expensemanager.core.designsystem4mp.generated.resources.yes_delete
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialogItem(
    confirm: () -> Unit,
    dismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { dismiss.invoke() },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
    ) {
        DeleteDialogContent(confirm, dismiss)
    }
}

@Composable
private fun DeleteDialogContent(
    confirm: () -> Unit,
    dismiss: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(resource = Res.string.delete),
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Text(
                modifier = Modifier,
                text = stringResource(resource = Res.string.delete_item_message),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f),
                    onClick = dismiss
                ) {
                    Text(text = stringResource(resource = Res.string.no_keep))
                }
                OutlinedButton(
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f),
                    onClick = confirm
                ) {
                    Text(text = stringResource(resource = Res.string.yes_delete))
                }
            }
            Spacer(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
@AppPreviewsLightAndDarkMode
fun DeleteDialogPreview() {
    ExpenseManagerTheme {
        DeleteDialogContent({}, {})
    }
}