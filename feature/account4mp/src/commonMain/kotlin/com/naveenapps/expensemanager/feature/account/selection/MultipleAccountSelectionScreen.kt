package com.naveenapps.expensemanager.feature.account.selection

import NotificationDuration
import NotificationType
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.SelectionTitle
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.model.AccountUiModel
import com.naveenapps.expensemanager.feature.account.list.AccountCheckedItem
import com.naveenapps.expensemanager.feature.account.list.getRandomAccountUiModel
import createNotification
import expensemanager.feature.account4mp.generated.resources.Res
import expensemanager.feature.account4mp.generated.resources.account_selection_message
import expensemanager.feature.account4mp.generated.resources.clear_all
import expensemanager.feature.account4mp.generated.resources.select
import expensemanager.feature.account4mp.generated.resources.select_account
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MultipleAccountSelectionScreen(
    viewModel: AccountSelectionViewModel = koinViewModel(),
    selectedAccounts: List<AccountUiModel> = emptyList(),
    onItemSelection: ((List<AccountUiModel>, Boolean) -> Unit)? = null,
) {
    viewModel.selectAllThisAccount(selectedAccounts)

    AccountSelectionView(viewModel, onItemSelection)
}

@Composable
private fun AccountSelectionView(
    viewModel: AccountSelectionViewModel,
    onItemSelection: ((List<AccountUiModel>, Boolean) -> Unit)?,
) {
    val accounts by viewModel.accounts.collectAsState()
    val selectedAccounts by viewModel.selectedAccounts.collectAsState()
    val msg = stringResource(Res.string.account_selection_message)

    MultipleAccountSelectionScreen(
        modifier = Modifier,
        accounts = accounts,
        selectedAccounts = selectedAccounts,
        onApplyChanges = {
            if (selectedAccounts.isNotEmpty()) {
                onItemSelection?.invoke(
                    selectedAccounts,
                    selectedAccounts.size == accounts.size,
                )
            } else {
                createNotification(NotificationType.TOAST)
                    .show(msg, duration = NotificationDuration.LONG)
            }
        },
        onClearChanges = viewModel::clearChanges,
        onItemSelection = viewModel::selectThisAccount,
    )
}

@Composable
private fun MultipleAccountSelectionScreen(
    modifier: Modifier = Modifier,
    accounts: List<AccountUiModel>,
    selectedAccounts: List<AccountUiModel>,
    onApplyChanges: (() -> Unit),
    onClearChanges: (() -> Unit),
    onItemSelection: ((AccountUiModel, Boolean) -> Unit)? = null,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            SelectionTitle(
                stringResource(resource = Res.string.select_account),
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
        items(accounts, key = { it.id }) { account ->
            val isSelected = selectedAccounts.fastAny { it.id == account.id }
            AccountCheckedItem(
                modifier = Modifier
                    .clickable {
                        onItemSelection?.invoke(account, isSelected.not())
                    }
                    .padding(start = 16.dp, end = 16.dp),
                name = account.name,
                icon = account.storedIcon.name,
                iconBackgroundColor = account.storedIcon.backgroundColor,
                isSelected = isSelected,
                onCheckedChange = {
                    onItemSelection?.invoke(account, it)
                },
            )
        }
        item {
            Column {
                HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                ) {
                    TextButton(onClick = onClearChanges) {
                        Text(text = stringResource(resource = Res.string.clear_all).uppercase())
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = onApplyChanges) {
                        Text(text = stringResource(resource = Res.string.select).uppercase())
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@AppPreviewsLightAndDarkMode
@Composable
private fun MultipleAccountSelectionScreenPreview() {
    ExpenseManagerTheme {
        val accounts = getRandomAccountUiModel(10)
        MultipleAccountSelectionScreen(
            accounts = accounts,
            selectedAccounts = accounts,
            onApplyChanges = {},
            onClearChanges = {},
            onItemSelection = null,
        )
    }
}
