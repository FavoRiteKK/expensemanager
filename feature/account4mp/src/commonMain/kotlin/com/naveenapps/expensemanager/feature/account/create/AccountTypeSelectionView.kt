package com.naveenapps.expensemanager.feature.account.create

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
import com.naveenapps.expensemanager.core.model.AccountType
import expensemanager.feature.account4mp.generated.resources.Res
import expensemanager.feature.account4mp.generated.resources.bank_account
import expensemanager.feature.account4mp.generated.resources.credit
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AccountTypeSelectionView(
    selectedAccountType: AccountType,
    onAccountTypeChange: ((AccountType) -> Unit),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AppFilterChip(
            modifier = Modifier.align(Alignment.CenterVertically),
            filterName = stringResource(resource = Res.string.bank_account),
            isSelected = selectedAccountType == AccountType.REGULAR,
            filterIcon = Icons.Default.ArrowDownward,
            filterSelectedColor = GREEN_500,
            onClick = {
                onAccountTypeChange.invoke(AccountType.REGULAR)
            },
        )
        AppFilterChip(
            modifier = Modifier.align(Alignment.CenterVertically),
            filterName = stringResource(resource = Res.string.credit),
            isSelected = selectedAccountType == AccountType.CREDIT,
            filterIcon = Icons.Default.ArrowUpward,
            filterSelectedColor = RED_500,
            onClick = {
                onAccountTypeChange.invoke(AccountType.CREDIT)
            },
        )
    }
}

@Preview
@Composable
private fun AccountTypeSelectionViewPreview() {
    ExpenseManagerTheme {
        Column {
            AccountTypeSelectionView(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                selectedAccountType = AccountType.REGULAR,
                onAccountTypeChange = {},
            )
            AccountTypeSelectionView(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                selectedAccountType = AccountType.CREDIT,
                onAccountTypeChange = {},
            )
        }
    }
}
