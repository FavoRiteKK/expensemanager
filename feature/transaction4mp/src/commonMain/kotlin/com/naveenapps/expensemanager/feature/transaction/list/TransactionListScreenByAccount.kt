package com.naveenapps.expensemanager.feature.transaction.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.fromCompleteDate
import com.naveenapps.expensemanager.core.common.utils.toDate
import com.naveenapps.expensemanager.core.common.utils.toDay
import com.naveenapps.expensemanager.core.common.utils.toMonthYear
import com.naveenapps.expensemanager.core.designsystem.components.EmptyItem
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppTopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.utils.ItemSpecModifier
import com.naveenapps.expensemanager.core.model.TransactionUiItem
import com.naveenapps.expensemanager.feature.filter.FilterView
import expensemanager.feature.transaction4mp.generated.resources.Res
import expensemanager.feature.transaction4mp.generated.resources.no_transactions_available
import expensemanager.feature.transaction4mp.generated.resources.transaction
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionListScreenByAccount(
    viewModel: TransactionListViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    TransactionListScreenContent(
        state,
        viewModel::processAction
    )
}

@Composable
private fun TransactionListScreenContent(
    state: TransactionListState,
    onAction: (TransactionListAction) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopNavigationBar(
                title = stringResource(Res.string.transaction),
                navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
                navigationBackClick = {
                    onAction.invoke(TransactionListAction.ClosePage)
                }
            )
        },
    ) { innerPadding ->
        TransactionListScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            state = state,
        ) { transaction ->
            onAction.invoke(TransactionListAction.OpenEdiTransaction(transaction.id))
        }
    }
}

@Composable
private fun TransactionListScreen(
    state: TransactionListState,
    modifier: Modifier = Modifier,
    onItemClick: ((TransactionUiItem) -> Unit)? = null,
) {

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            FilterView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 6.dp),
            )
        }
        if (state.transactionListItem.isEmpty()) {
            item {
                EmptyItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(400.dp),
                    emptyItemText = stringResource(resource = Res.string.no_transactions_available),
                    icon = "ic_no_transaction"
                )
            }
        } else {

            items(state.transactionListItem) { transactionListItem ->
                when (transactionListItem) {
                    TransactionListItem.Divider -> {
                        HorizontalDivider(
                            modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 8.dp
                            )
                        )
                    }

                    is TransactionListItem.HeaderItem -> {
                        TransactionHeaderItem(
                            transactionListItem.date,
                            transactionListItem.amountTextColor,
                            transactionListItem.totalAmount,
                        )
                    }

                    is TransactionListItem.TransactionItem -> {
                        val item = transactionListItem.date
                        TransactionItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onItemClick?.invoke(item)
                                }
                                .then(ItemSpecModifier),
                            categoryName = item.categoryName,
                            categoryColor = item.categoryIcon.backgroundColor,
                            categoryIcon = item.categoryIcon.name,
                            amount = item.amount,
                            date = item.date,
                            notes = item.notes,
                            transactionType = item.transactionType,
                            fromAccountName = item.fromAccountName,
                            fromAccountIcon = item.fromAccountIcon.name,
                            fromAccountColor = item.fromAccountIcon.backgroundColor,
                            toAccountName = item.toAccountName,
                            toAccountIcon = item.toAccountIcon?.name,
                            toAccountColor = item.toAccountIcon?.backgroundColor,
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
private fun TransactionHeaderItem(
    date: String,
    textColor: Int,
    totalAmount: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 4.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = date.fromCompleteDate().toDate(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = date.fromCompleteDate().toMonthYear(),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = date.fromCompleteDate().toDay(),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            text = totalAmount,
            color = Color(color = textColor),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
