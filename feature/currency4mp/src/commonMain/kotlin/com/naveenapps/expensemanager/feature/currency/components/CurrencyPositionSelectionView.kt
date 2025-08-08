package com.naveenapps.expensemanager.feature.currency.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppFilterChip
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.model.TextPosition
import expensemanager.feature.currency4mp.generated.resources.Res
import expensemanager.feature.currency4mp.generated.resources.prefix_amount
import expensemanager.feature.currency4mp.generated.resources.suffix_amount
import org.jetbrains.compose.resources.stringResource

@Composable
fun TextFormatSelectionView(
    selectedCurrencyPositionType: TextPosition,
    currency: String,
    onCurrencyPositionTypeChange: ((TextPosition) -> Unit),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AppFilterChip(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            filterName = stringResource(resource = Res.string.prefix_amount, currency),
            isSelected = selectedCurrencyPositionType == TextPosition.PREFIX,
            onClick = {
                onCurrencyPositionTypeChange.invoke(TextPosition.PREFIX)
            },
        )
        AppFilterChip(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            filterName = stringResource(resource = Res.string.suffix_amount, currency),
            isSelected = selectedCurrencyPositionType == TextPosition.SUFFIX,
            onClick = {
                onCurrencyPositionTypeChange.invoke(TextPosition.SUFFIX)
            },
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
private fun CurrencyPositionTypeSelectionViewPreview() {
    ExpenseManagerTheme {
        Column {
            TextFormatSelectionView(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                selectedCurrencyPositionType = TextPosition.PREFIX,
                currency = "$",
                onCurrencyPositionTypeChange = {},
            )
            TextFormatSelectionView(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                selectedCurrencyPositionType = TextPosition.SUFFIX,
                currency = "$",
                onCurrencyPositionTypeChange = {},
            )
        }
    }
}
