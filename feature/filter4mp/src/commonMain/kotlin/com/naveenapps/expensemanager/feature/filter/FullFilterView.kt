package com.naveenapps.expensemanager.feature.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.toCapitalize
import com.naveenapps.expensemanager.feature.filter.datefilter.DateFilterSelectionView
import com.naveenapps.expensemanager.feature.filter.type.FilterTypeSelectionView
import com.naveenapps.expensemanager.feature.filter.type.InputChipView
import expensemanager.feature.filter4mp.generated.resources.Res
import expensemanager.feature.filter4mp.generated.resources.warning_filter_out_scope
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullFilterView(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = koinViewModel(parameters = { parametersOf("") })
) {
    val filterState by viewModel.filterState.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (filterState.showDateFilter) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.processAction(FilterAction.DismissDateFilter)
            },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            DateFilterSelectionView(
                onComplete = {
                    viewModel.processAction(FilterAction.DismissDateFilter)
                }
            )
        }
    }

    if (filterState.showTypeFilter) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.processAction(FilterAction.DismissTypeFilter)
            },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            FilterTypeSelectionView(
                applyChanges = {
                    viewModel.processAction(FilterAction.DismissTypeFilter)
                },
            )
        }
    }

    Column(modifier = modifier) {
        FilterContentView(
            modifier = modifier,
            filterState = filterState,
            onAction = viewModel::processAction,
        )
        TypeFilter(
            modifier = Modifier.padding(horizontal = 16.dp),
            filterState = filterState,
            onAction = viewModel::processAction,
        )
    }
}

@Composable
private fun FilterContentView(
    filterState: FilterState,
    onAction: (FilterAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .height(40.dp)
                .clickable {
                    onAction.invoke(FilterAction.ShowDateFilter)
                },
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp),
                imageVector = Icons.Default.EditCalendar,
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                text = filterState.date,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        IconButton(
            onClick = {
                onAction.invoke(FilterAction.MoveDateBackward)
            },
            enabled = filterState.showBackward,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
            )
        }
        IconButton(
            onClick = {
                onAction.invoke(FilterAction.MoveDateForward)
            },
            enabled = filterState.showForward,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
            )
        }
        IconButton(onClick = {
            onAction.invoke(FilterAction.ShowTypeFilter)
        }) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun TypeFilter(
    filterState: FilterState,
    onAction: (FilterAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!filterState.todayIncluded) {
        Row(modifier = modifier) {
            Text(
                text = stringResource(Res.string.warning_filter_out_scope),
                modifier = Modifier.weight(1f)
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
            )
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        filterState.selectedTransactionTypes.forEach { type ->
            InputChipView(
                label = type.toCapitalize(),
                selected = true,
            ) {
                onAction.invoke(FilterAction.RemoveTransactionType(type))
            }
        }
        filterState.selectedAccounts.forEach { account ->
            InputChipView(account.name, true, iconName = account.storedIcon.name) {
                onAction.invoke(FilterAction.RemoveAccount(account))
            }
        }
        filterState.selectedCategories.forEach { category ->
            InputChipView(category.name, true, iconName = category.storedIcon.name) {
                onAction.invoke(FilterAction.RemoveCategory(category))
            }
        }
    }
}

//@AppPreviewsLightAndDarkMode
//@Composable
//fun FilterViewPreview() {
//    ExpenseManagerTheme {
//        Column {
//            FilterContentView(
//                filterState = FilterState(
//                    date = "This Month (11/2023)",
//                    dateRangeType = DateRangeType.ALL,
//                    selectedCategories = emptyList(),
//                    allAccounts = emptyList(),
//                    selectedAccounts = emptyList(),
//                    selectedTransactionTypes = emptyList(),
//                    showForward = false,
//                    showBackward = false,
//                    showDateFilter = false,
//                    showTypeFilter = false,
//                    todayIncluded = false
//                ),
//                onAction = {},
//                modifier = Modifier.fillMaxWidth(),
//            )
//            FilterContentView(
//                filterState = FilterState(
//                    date = "This Month (11/2023)",
//                    dateRangeType = DateRangeType.ALL,
//                    selectedCategories = emptyList(),
//                    allAccounts = emptyList(),
//                    selectedAccounts = emptyList(),
//                    selectedTransactionTypes = listOf(TransactionType.TRANSFER, TransactionType.INCOME),
//                    showForward = false,
//                    showBackward = false,
//                    showDateFilter = false,
//                    showTypeFilter = false,
//                    todayIncluded = true
//                ),
//                onAction = {},
//                modifier = Modifier.fillMaxWidth(),
//            )
//        }
//    }
//}
