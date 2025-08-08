package com.naveenapps.expensemanager.feature.settings.advanced

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme

@Preview
@Composable
fun AdvancedSettingsPreview() {
    ExpenseManagerTheme {
        AdvancedSettingsScaffoldView(
            state = AdvancedSettingState(
                accounts = getRandomAccountData(5),
                selectedAccount = getRandomAccountData(5).firstOrNull(),
                expenseCategories = getRandomCategoryData(5),
                selectedExpenseCategory = getRandomCategoryData(5).firstOrNull(),
                incomeCategories = getRandomCategoryData(5),
                selectedIncomeCategory = getRandomCategoryData(5).firstOrNull(),
                showDateFilter = false
            ),
            onAction = {}
        )
    }
}
