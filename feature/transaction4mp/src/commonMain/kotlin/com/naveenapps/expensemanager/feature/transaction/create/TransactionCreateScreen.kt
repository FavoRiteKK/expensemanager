package com.naveenapps.expensemanager.feature.transaction.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.BLUE_500
import com.naveenapps.expensemanager.core.common.utils.toCompleteDateWithDate
import com.naveenapps.expensemanager.core.designsystem.components.DeleteDialogItem
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppDatePickerDialog
import com.naveenapps.expensemanager.core.designsystem.ui.components.ClickableTextField
import com.naveenapps.expensemanager.core.designsystem.ui.components.DecimalTextField
import com.naveenapps.expensemanager.core.designsystem.ui.components.TopNavigationBarWithDeleteAction
import com.naveenapps.expensemanager.core.designsystem.ui.utils.ItemSpecModifier
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.feature.account.list.AccountItem
import com.naveenapps.expensemanager.feature.account.selection.AccountSelectionScreen
import com.naveenapps.expensemanager.feature.category.list.CategoryItem
import com.naveenapps.expensemanager.feature.category.selection.CategorySelectionScreen
import com.naveenapps.expensemanager.feature.transaction.numberpad.NumberPadDialogView
import expensemanager.feature.transaction4mp.generated.resources.Res
import expensemanager.feature.transaction4mp.generated.resources.amount
import expensemanager.feature.transaction4mp.generated.resources.amount_error_message
import expensemanager.feature.transaction4mp.generated.resources.from_account
import expensemanager.feature.transaction4mp.generated.resources.notes
import expensemanager.feature.transaction4mp.generated.resources.optional_details
import expensemanager.feature.transaction4mp.generated.resources.select_account
import expensemanager.feature.transaction4mp.generated.resources.select_category
import expensemanager.feature.transaction4mp.generated.resources.select_date
import expensemanager.feature.transaction4mp.generated.resources.to_account
import expensemanager.feature.transaction4mp.generated.resources.transaction
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransactionCreateScreen(
    viewModel: TransactionCreateViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    TransactionCreateScreenContent(
        state = state,
        onAction = viewModel::processAction
    )
}

@Composable
private fun TransactionCreateScreenContent(
    state: TransactionCreateState,
    onAction: (TransactionCreateAction) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    if (state.showDeleteDialog) {
        DeleteDialogItem(
            confirm = {
                onAction.invoke(TransactionCreateAction.Delete)
            },
            dismiss = {
                onAction.invoke(TransactionCreateAction.DismissDeleteDialog)
            }
        )
    } else if (state.showNumberPad) {
        NumberPadDialogView(
            onConfirm = { amount ->
                onAction.invoke(TransactionCreateAction.SetNumberPadValue(amount))
            },
        )
    } else if (state.showCategorySelection) {
        CategorySelectionView(state, onAction)
    } else if (state.showAccountSelection) {
        AccountSelectionView(state, onAction)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopNavigationBarWithDeleteAction(
                title = stringResource(resource = Res.string.transaction),
                isDeleteEnabled = state.showDeleteButton,
                onNavigationIconClick = {
                    onAction.invoke(TransactionCreateAction.ClosePage)
                },
                onDeleteActionClick = {
                    onAction.invoke(TransactionCreateAction.ShowDeleteDialog)
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction.invoke(TransactionCreateAction.Save)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "",
                )
            }
        },
    ) { innerPadding ->

        TransactionCreateScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = state,
            onAction = onAction
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AccountSelectionView(
    state: TransactionCreateState,
    onAction: (TransactionCreateAction) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = {
            onAction.invoke(TransactionCreateAction.DismissAccountSelection)
        },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
    ) {
        AccountSelectionScreen(
            accounts = state.accounts,
            selectedAccount = when (state.accountSelection) {
                AccountSelection.FROM_ACCOUNT -> state.selectedFromAccount
                AccountSelection.TO_ACCOUNT -> state.selectedToAccount
            },
            createNewCallback = {
                onAction.invoke(TransactionCreateAction.OpenCategoryCreate(null))
            },
            onItemSelection = {
                onAction.invoke(TransactionCreateAction.SelectAccount(it))
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategorySelectionView(
    state: TransactionCreateState,
    onAction: (TransactionCreateAction) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onAction.invoke(TransactionCreateAction.DismissCategorySelection)
        },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
    ) {
        CategorySelectionScreen(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            createNewCallback = {
                onAction.invoke(TransactionCreateAction.OpenCategoryCreate(null))
            },
            onItemSelection = {
                onAction.invoke(TransactionCreateAction.SelectCategory(it))
            }
        )
    }
}

@Composable
private fun TransactionCreateScreen(
    state: TransactionCreateState,
    onAction: (TransactionCreateAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    if (state.showDateSelection) {
        AppDatePickerDialog(
            selectedDate = state.dateTime,
            onDateSelected = {
                onAction.invoke(TransactionCreateAction.SelectDate(it))
            },
            onDismiss = {
                onAction.invoke(TransactionCreateAction.DismissDateSelection)
            }
        )
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        TransactionTypeSelectionView(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .align(Alignment.CenterHorizontally),
            selectedTransactionType = state.transactionType,
            onTransactionTypeChange = {
                onAction.invoke(TransactionCreateAction.ChangeTransactionType(it))
            },
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth(),
        ) {
            ClickableTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                value = state.dateTime.toCompleteDateWithDate(),
                label = Res.string.select_date,
                leadingIcon = Icons.Outlined.EditCalendar,
                onClick = {
                    focusManager.clearFocus(force = true)
                    onAction.invoke(TransactionCreateAction.ShowDateSelection)
                },
            )
        }

        DecimalTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth(),
            value = state.amount.value,
            isError = state.amount.valueError,
            onValueChange = state.amount.onValueChange,
            leadingIconText = state.currency.symbol,
            label = Res.string.amount,
            errorMessage = stringResource(resource = Res.string.amount_error_message),
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction.invoke(TransactionCreateAction.ShowNumberPad)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Calculate,
                        contentDescription = "",
                    )
                }
            },
        )

        if (state.transactionType != TransactionType.TRANSFER) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                text = stringResource(resource = Res.string.select_category),
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                color = Color(color = BLUE_500),
            )
            CategoryItem(
                name = state.selectedCategory.name,
                icon = state.selectedCategory.storedIcon.name,
                iconBackgroundColor = state.selectedCategory.storedIcon.backgroundColor,
                endIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        focusManager.clearFocus(force = true)
                        onAction.invoke(TransactionCreateAction.ShowCategorySelection)
                    }
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            text = stringResource(
                resource = if (state.transactionType == TransactionType.TRANSFER) {
                    Res.string.from_account
                } else {
                    Res.string.select_account
                },
            ),
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            color = Color(color = BLUE_500),
        )

        AccountItem(
            name = state.selectedFromAccount.name,
            icon = state.selectedFromAccount.storedIcon.name,
            iconBackgroundColor = state.selectedFromAccount.storedIcon.backgroundColor,
            endIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            amount = state.selectedFromAccount.amount.amountString,
            amountTextColor = state.selectedFromAccount.amountTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focusManager.clearFocus(force = true)
                    onAction.invoke(
                        TransactionCreateAction.ShowAccountSelection(
                            AccountSelection.FROM_ACCOUNT
                        )
                    )
                }
                .then(ItemSpecModifier),
        )
        if (state.transactionType == TransactionType.TRANSFER) {
            Text(
                modifier = Modifier
                    .then(ItemSpecModifier)
                    .fillMaxWidth(),
                text = stringResource(resource = Res.string.to_account),
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                color = Color(color = BLUE_500),
            )

            AccountItem(
                name = state.selectedToAccount.name,
                icon = state.selectedToAccount.storedIcon.name,
                iconBackgroundColor = state.selectedToAccount.storedIcon.backgroundColor,
                endIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                amount = state.selectedToAccount.amount.amountString,
                amountTextColor = state.selectedFromAccount.amountTextColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        focusManager.clearFocus(force = true)
                        onAction.invoke(
                            TransactionCreateAction.ShowAccountSelection(
                                AccountSelection.TO_ACCOUNT
                            )
                        )
                    }
                    .padding(16.dp),
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                .fillMaxWidth(),
            value = state.notes.value,
            singleLine = true,
            leadingIcon =
                {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Notes,
                        contentDescription = "",
                    )
                },
            label = {
                Text(text = stringResource(resource = Res.string.notes))
            },
            onValueChange = {
                state.notes.onValueChange?.invoke(it)
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(force = true)
                },
            ),
            supportingText = {
                Text(text = stringResource(resource = Res.string.optional_details))
            },
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp),
        )
    }
}


//@Preview
//@Composable
//private fun TransactionCreateStatePreview() {
//
//    val amountField = TextFieldValue(value = "", valueError = false, onValueChange = {})
//
//    ExpenseManagerTheme {
//        TransactionCreateScreenContent(
//            state = TransactionCreateState(
//                amount = amountField,
//                currency = Currency(symbol = "$", name = ""),
//                dateTime = Clock.System.now().asCurrentDateTime(),
//                notes = amountField,
//                selectedCategory = Category(
//                    id = "1",
//                    name = "Shopping",
//                    type = CategoryType.EXPENSE,
//                    StoredIcon(
//                        name = "account_balance_wallet",
//                        backgroundColor = "#FF000000",
//                    ),
//                    createdOn = Clock.System.now().asCurrentDateTime(),
//                    updatedOn = Clock.System.now().asCurrentDateTime(),
//                ),
//                selectedFromAccount = AccountUiModel(
//                    id = "1",
//                    name = "Shopping",
//                    type = AccountType.REGULAR,
//                    storedIcon = StoredIcon(
//                        name = "account_balance_wallet",
//                        backgroundColor = "#FF000000",
//                    ),
//                    amountTextColor = RED_500,
//                    amount = Amount(0.0, "$ 0.00"),
//                ),
//                selectedToAccount = AccountUiModel(
//                    id = "1",
//                    name = "Shopping",
//                    type = AccountType.REGULAR,
//                    storedIcon = StoredIcon(
//                        name = "account_balance_wallet",
//                        backgroundColor = "#FF000000",
//                    ),
//                    amountTextColor = GREEN_500,
//                    amount = Amount(0.0, "$ 0.00"),
//                ),
//                accounts = emptyList(),
//                categories = emptyList(),
//                showDeleteButton = false,
//                showDeleteDialog = false,
//                showCategorySelection = false,
//                showAccountSelection = false,
//                showNumberPad = false,
//                transactionType = TransactionType.TRANSFER,
//                accountSelection = AccountSelection.FROM_ACCOUNT,
//                showTimeSelection = false,
//                showDateSelection = false
//            ),
//            onAction = {}
//        )
//    }
//}
