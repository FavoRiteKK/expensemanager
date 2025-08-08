package com.naveenapps.expensemanager.feature.transaction.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.GREEN_500
import com.naveenapps.expensemanager.core.common.utils.RED_500
import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.common.utils.fromCompleteDate
import com.naveenapps.expensemanager.core.common.utils.toCompleteDateWithDate
import com.naveenapps.expensemanager.core.common.utils.toDate
import com.naveenapps.expensemanager.core.common.utils.toDay
import com.naveenapps.expensemanager.core.common.utils.toMonthYear
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.components.EmptyItem
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppTopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.components.IconAndBackgroundView
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.ui.utils.ItemSpecModifier
import com.naveenapps.expensemanager.core.designsystem.ui.utils.getColorValue
import com.naveenapps.expensemanager.core.designsystem.utils.Exports
import com.naveenapps.expensemanager.core.model.Amount
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.core.model.TransactionGroup
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.core.model.TransactionUiItem
import com.naveenapps.expensemanager.feature.filter.FilterView
import expensemanager.feature.transaction4mp.generated.resources.Res
import expensemanager.feature.transaction4mp.generated.resources.no_transactions_available
import expensemanager.feature.transaction4mp.generated.resources.transaction
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun TransactionListScreen(
    showBackNavigationIcon: Boolean = false,
    viewModel: TransactionListViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    TransactionListScreenContent(
        showBackNavigationIcon,
        state,
        viewModel::processAction
    )
}

@Composable
private fun TransactionListScreenContent(
    showBackNavigationIcon: Boolean,
    state: TransactionListState,
    onAction: (TransactionListAction) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopNavigationBar(
                title = stringResource(Res.string.transaction),
                navigationIcon = if (showBackNavigationIcon) Icons.AutoMirrored.Default.ArrowBack else null,
                navigationBackClick = {
                    onAction.invoke(TransactionListAction.ClosePage)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction.invoke(TransactionListAction.OpenCreateTransaction)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                )
            }
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
fun TransactionHeaderItem(
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

@Composable
fun TransactionItem(
    categoryName: String,
    fromAccountName: String,
    fromAccountIcon: String,
    fromAccountColor: String,
    amount: Amount,
    date: String,
    notes: String?,
    categoryColor: String,
    categoryIcon: String,
    modifier: Modifier = Modifier,
    toAccountName: String? = null,
    toAccountIcon: String? = null,
    toAccountColor: String? = null,
    transactionType: TransactionType = TransactionType.EXPENSE,
) {
    val isTransfer = toAccountName?.isNotBlank()

    Row(modifier = modifier) {
        IconAndBackgroundView(
            modifier = Modifier.align(Alignment.CenterVertically),
            icon = if (isTransfer == true) {
                "ic_transfer_account"
            } else {
                categoryIcon
            },
            iconBackgroundColor =
                if (isTransfer == true) {
                    "#166EF7"
                } else {
                    categoryColor
                },
            name = categoryName,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
        ) {
            if (isTransfer == true && toAccountIcon != null && toAccountColor != null) {
                AccountNameWithIcon(
                    fromAccountIcon,
                    fromAccountColor,
                    fromAccountName,
                )
                AccountNameWithIcon(
                    toAccountIcon,
                    toAccountColor,
                    toAccountName,
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = categoryName,
                    style = MaterialTheme.typography.bodyLarge,
                )
                AccountNameWithIcon(
                    fromAccountIcon,
                    fromAccountColor,
                    fromAccountName,
                )
                if (notes?.isNotBlank() == true) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = notes,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = amount.amountString ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = when (transactionType) {
                    TransactionType.EXPENSE -> Color(RED_500)
                    TransactionType.INCOME -> Color(GREEN_500)
                    else -> Color.Unspecified
                },
            )
            Text(
                modifier = Modifier.align(Alignment.End),
                text = date,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun AccountNameWithIcon(
    fromAccountIcon: String,
    fromAccountColor: String,
    fromAccountName: String,
) {
    Row {
        Icon(
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(resource = Exports.drawableBy(fromAccountIcon)),
            contentDescription = "",
            tint = Color(getColorValue(fromAccountColor)),
        )
        Text(
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth(),
            text = fromAccountName,
            color = Color(getColorValue(fromAccountColor)),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

//@AppPreviewsLightAndDarkMode
//@Composable
//fun TransactionUiStatePreview() {
//    ExpenseManagerTheme {
//        TransactionItem(
//            categoryName = "Utilities",
//            categoryIcon = "agriculture",
//            categoryColor = "#A65A56",
//            fromAccountName = "Card-xxx",
//            fromAccountIcon = "account_balance",
//            fromAccountColor = "#A65A56",
//            amount = Amount(amount = 300.0, amountString = "300 ₹"),
//            date = "15/11/2019",
//            notes = "Sample notes given as per transaction",
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.White)
//                .padding(16.dp),
//        )
//    }
//}

@AppPreviewsLightAndDarkMode
@Composable
fun TransactionListItemEmptyStatePreview() {
    ExpenseManagerTheme {
        TransactionListScreen(
            state = TransactionListState(emptyList()),
            modifier = Modifier.fillMaxSize(),
        )
    }
}

val DUMMY_DATA = listOf(
    getTransactionUiState(),
    getTransactionUiState(),
    getTransactionUiState(),
)

@OptIn(ExperimentalTime::class)
fun getTransactionItem() = TransactionUiItem(
    id = "1",
    notes = "Sample Description",
    amount = Amount(amount = 300.0, amountString = "300.00 ₹"),
    categoryName = "Clothing",
    transactionType = TransactionType.EXPENSE,
    categoryIcon = StoredIcon(
        name = "agriculture",
        backgroundColor = "#FF000000",
    ),
    fromAccountName = "DB Bank xxxx",
    fromAccountIcon = StoredIcon(
        name = "account_balance",
        backgroundColor = "#FF000000",
    ),
    date = Clock.System.now().asCurrentDateTime().toCompleteDateWithDate(),
)

private fun getTransactionUiState() = TransactionGroup(
    date = "12/10/2023",
    amountTextColor = RED_500,
    totalAmount = Amount(amount = 300.0, amountString = "300.00 ₹"),
    transactions = buildList {
        repeat(3) {
            add(getTransactionItem())
        }
    },
)

@AppPreviewsLightAndDarkMode
@Composable
fun TransactionListItemSuccessStatePreview() {
    ExpenseManagerTheme {
        TransactionListScreenContent(
            state = TransactionListState(DUMMY_DATA.convertGroupToTransactionListItems()),
            showBackNavigationIcon = true,
            onAction = {}
        )
    }
}
