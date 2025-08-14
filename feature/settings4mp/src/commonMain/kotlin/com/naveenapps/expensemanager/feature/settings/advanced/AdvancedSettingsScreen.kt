package com.naveenapps.expensemanager.feature.settings.advanced

import Notify
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Reorder
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.TopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.ui.utils.ItemSpecModifier
import com.naveenapps.expensemanager.core.designsystem.utils.ObserveAsEvents
import com.naveenapps.expensemanager.core.model.Account
import com.naveenapps.expensemanager.core.model.AccountType
import com.naveenapps.expensemanager.core.model.Category
import com.naveenapps.expensemanager.core.model.CategoryType
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.feature.filter.datefilter.DateFilterSelectionView
import expensemanager.feature.settings4mp.generated.resources.Res
import expensemanager.feature.settings4mp.generated.resources.accounts_re_order
import expensemanager.feature.settings4mp.generated.resources.accounts_re_order_message
import expensemanager.feature.settings4mp.generated.resources.advanced
import expensemanager.feature.settings4mp.generated.resources.backup
import expensemanager.feature.settings4mp.generated.resources.backup_fail
import expensemanager.feature.settings4mp.generated.resources.backup_message
import expensemanager.feature.settings4mp.generated.resources.backup_success
import expensemanager.feature.settings4mp.generated.resources.default_account
import expensemanager.feature.settings4mp.generated.resources.default_expense_category
import expensemanager.feature.settings4mp.generated.resources.default_income_category
import expensemanager.feature.settings4mp.generated.resources.default_selected_items
import expensemanager.feature.settings4mp.generated.resources.filter
import expensemanager.feature.settings4mp.generated.resources.filter_message
import expensemanager.feature.settings4mp.generated.resources.others
import expensemanager.feature.settings4mp.generated.resources.restore
import expensemanager.feature.settings4mp.generated.resources.restore_and_backup
import expensemanager.feature.settings4mp.generated.resources.restore_fail
import expensemanager.feature.settings4mp.generated.resources.restore_message
import expensemanager.feature.settings4mp.generated.resources.restore_success
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private val logger = KotlinLogging.logger {}

@Composable
fun AdvancedSettingsScreen(
    viewModel: AdvancedSettingsViewModel = koinViewModel(),
) {
    val restoreSuccessMsg = stringResource(Res.string.restore_success)
    val restoreFailMsg = stringResource(Res.string.restore_fail)
    val backupSuccessMsg = stringResource(Res.string.backup_success)
    val backupFailMsg = stringResource(Res.string.backup_fail)

    ObserveAsEvents(viewModel.event) {
        when (it) {
            AdvancedSettingEvent.RestoreSuccess -> {
                Notify(message = restoreSuccessMsg)
            }

            AdvancedSettingEvent.RestoreFail -> {
                Notify(message = restoreFailMsg)
            }

            AdvancedSettingEvent.BackupFail -> {
                Notify(message = backupFailMsg)
            }

            AdvancedSettingEvent.BackupSuccess -> {
                Notify(message = backupSuccessMsg)
            }

        }
    }

    val state by viewModel.state.collectAsState()

    AdvancedSettingsScaffoldView(
        state = state,
        onAction = viewModel::processAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AdvancedSettingsScaffoldView(
    state: AdvancedSettingState,
    onAction: (AdvancedSettingAction) -> Unit,
) {
    logger.warn { "idk AdvancedSettingsScaffoldView" }

    if (state.message != null) {
        Notify(message = state.message)
    }

    if (state.showDateFilter) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction.invoke(AdvancedSettingAction.DismissDateFilterDialog)
            },
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            logger.warn { "idk AdvancedSettingsScaffoldView.ModalBottomSheet" }

            DateFilterSelectionView(
                onComplete = {
                    onAction.invoke(AdvancedSettingAction.DismissDateFilterDialog)
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopNavigationBar(
                onClick = {
                    onAction.invoke(AdvancedSettingAction.ClosePage)
                },
                title = stringResource(Res.string.advanced),
            )
        },
    ) { innerPadding ->
        logger.warn { "idk AdvancedSettingsScaffoldView.Scaffold" }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            logger.warn { "idk AdvancedSettingsScaffoldView.Scaffold.Column" }

            Text(
                modifier = Modifier.then(ItemSpecModifier),
                text = stringResource(resource = Res.string.default_selected_items),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
            if (state.accounts.isNotEmpty() && state.selectedAccount != null) {
                AccountPreSelectionView(
                    accounts = state.accounts,
                    selectedAccount = state.selectedAccount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onItemSelection = {
                        onAction.invoke(AdvancedSettingAction.SelectAccount(it))
                    },
                )
            }
            if (state.expenseCategories.isNotEmpty() && state.selectedExpenseCategory != null) {
                CategoryPreSelectionView(
                    state.expenseCategories,
                    state.selectedExpenseCategory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    label = Res.string.default_expense_category,
                    onItemSelection = {
                        onAction.invoke(AdvancedSettingAction.SelectExpenseCategory(it))
                    },
                )
            }
            if (state.incomeCategories.isNotEmpty() && state.selectedIncomeCategory != null) {
                CategoryPreSelectionView(
                    state.incomeCategories,
                    state.selectedIncomeCategory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    label = Res.string.default_income_category,
                    onItemSelection = {
                        onAction.invoke(AdvancedSettingAction.SelectIncomeCategory(it))
                    },
                )
            }

            Text(
                modifier = Modifier.then(ItemSpecModifier),
                text = stringResource(resource = Res.string.restore_and_backup),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )

            SettingsItem(
                modifier = Modifier
                    .clickable {
                        onAction.invoke(AdvancedSettingAction.Backup)
                    }
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.backup),
                description = stringResource(resource = Res.string.backup_message),
                imageVector = Icons.Outlined.Backup,
            )

            SettingsItem(
                modifier = Modifier
                    .clickable {
                        onAction.invoke(AdvancedSettingAction.Restore)
                    }
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.restore),
                description = stringResource(resource = Res.string.restore_message),
                imageVector = Icons.Outlined.Restore,
            )

            Text(
                modifier = Modifier.then(ItemSpecModifier),
                text = stringResource(resource = Res.string.others),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )

            SettingsItem(
                modifier = Modifier
                    .clickable {
                        onAction.invoke(AdvancedSettingAction.ShowDateFilterDialog)
                    }
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.filter),
                description = stringResource(resource = Res.string.filter_message),
                imageVector = Icons.Outlined.FilterAlt,
            )

            SettingsItem(
                modifier = Modifier
                    .clickable {
                        onAction.invoke(AdvancedSettingAction.OpenAccountReOrder)
                    }
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.accounts_re_order),
                description = stringResource(resource = Res.string.accounts_re_order_message),
                imageVector = Icons.Outlined.Reorder,
            )
        }
    }
}


@Composable
private fun SettingsItem(
    title: String,
    description: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            imageVector = imageVector,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
        ) {
            Text(text = title)
            Text(text = description, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AccountPreSelectionView(
    accounts: List<Account>,
    selectedAccount: Account,
    onItemSelection: (Account) -> Unit,
    modifier: Modifier = Modifier,
) {
    logger.warn { "idk AccountPreSelectionView" }

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = expanded.not()
        },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedAccount.name,
            onValueChange = { },
            label = {
                Text(stringResource(resource = Res.string.default_account))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            accounts.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item.name)
                    },
                    onClick = {
                        onItemSelection.invoke(item)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoryPreSelectionView(
    categories: List<Category>,
    selectedCategory: Category,
    label: StringResource,
    onItemSelection: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = expanded.not()
        },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedCategory.name,
            onValueChange = { },
            label = {
                Text(stringResource(resource = label))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            categories.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item.name)
                    },
                    onClick = {
                        onItemSelection.invoke(item)
                        expanded = false
                    },
                )
            }
        }
    }
}

fun getRandomAccountData(totalCount: Int = 10): List<Account> {
    return buildList {
        repeat(totalCount) {
            add(getAccountData(it))
        }
    }
}

fun getRandomCategoryData(totalCount: Int = 10): List<Category> {
    return buildList {
        repeat(totalCount) {
            add(getCategoryData(it))
        }
    }
}

@OptIn(ExperimentalTime::class)
fun getAccountData(
    index: Int,
    accountType: AccountType = AccountType.CREDIT,
    amount: Double = 0.0,
): Account {
    return Account(
        id = "$index",
        name = "Account $index",
        type = accountType,
        storedIcon = StoredIcon(
            name = "credit_card",
            backgroundColor = "#FF000000",
        ),
        amount = amount,
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    )
}

@OptIn(ExperimentalTime::class)
fun getCategoryData(
    index: Int,
    categoryType: CategoryType = CategoryType.EXPENSE,
): Category {
    return Category(
        id = "$index",
        name = "Account $index",
        type = categoryType,
        storedIcon = StoredIcon(
            name = "credit_card",
            backgroundColor = "#FF000000",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    )
}

@AppPreviewsLightAndDarkMode
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
                showDateFilter = false,
                message = null,
            ),
            onAction = {}
        )
    }
}
