package com.naveenapps.expensemanager.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.view_all
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardWidgetTitle(
    title: String,
    modifier: Modifier = Modifier,
    onActionClick: (() -> Unit)? = null,
    onActionText: StringResource = Res.string.view_all,
) {
    Box(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )
        if (onActionClick != null) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { onActionClick.invoke() },
                text = stringResource(resource = onActionText).uppercase(),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}
