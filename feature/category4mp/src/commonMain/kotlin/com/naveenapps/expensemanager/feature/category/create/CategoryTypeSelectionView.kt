package com.naveenapps.expensemanager.feature.category.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.GREEN_500
import com.naveenapps.expensemanager.core.common.utils.RED_500
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppFilterChip
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.model.CategoryType
import com.naveenapps.expensemanager.core.model.isExpense
import com.naveenapps.expensemanager.core.model.isIncome
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.expense
import expensemanager.core.designsystem4mp.generated.resources.income
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CategoryTypeSelectionView(
    selectedCategoryType: CategoryType,
    onCategoryTypeChange: ((CategoryType) -> Unit),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AppFilterChip(
            modifier = Modifier.align(Alignment.CenterVertically),
            filterName = stringResource(resource = Res.string.income),
            isSelected = selectedCategoryType.isIncome(),
            filterIcon = Icons.Default.ArrowDownward,
            filterSelectedColor = GREEN_500,
            onClick = {
                onCategoryTypeChange.invoke(CategoryType.INCOME)
            },
        )
        AppFilterChip(
            modifier = Modifier.align(Alignment.CenterVertically),
            filterName = stringResource(resource = Res.string.expense),
            isSelected = selectedCategoryType.isExpense(),
            filterIcon = Icons.Default.ArrowUpward,
            filterSelectedColor = RED_500,
            onClick = {
                onCategoryTypeChange.invoke(CategoryType.EXPENSE)
            },
        )
    }
}

@Preview
@Composable
private fun CategoryTypeSelectionViewPreview() {
    ExpenseManagerTheme {
        Column {
            CategoryTypeSelectionView(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                selectedCategoryType = CategoryType.EXPENSE,
                onCategoryTypeChange = {},
            )
            CategoryTypeSelectionView(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                selectedCategoryType = CategoryType.INCOME,
                onCategoryTypeChange = {},
            )
        }
    }
}
