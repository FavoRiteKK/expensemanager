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
import com.naveenapps.expensemanager.core.model.AccountUiModel
import com.naveenapps.expensemanager.feature.filter.datefilter.DateFilterSelectionView
import com.naveenapps.expensemanager.feature.filter.type.AccountChipWithDropdown
import expensemanager.feature.filter4mp.generated.resources.Res
import expensemanager.feature.filter4mp.generated.resources.warning_filter_out_scope
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFilterView(
    accId: String,
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = koinViewModel(parameters = { parametersOf(accId) })
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

    Column(modifier = modifier) {
        FilterContentView(
            modifier = modifier,
            filterState = filterState,
            onAction = viewModel::processAction,
        )
        TypeFilter(
            modifier = Modifier.padding(horizontal = 16.dp),
            filterState = filterState,
            onSelected = {
                viewModel.processAction(FilterAction.UpdateFilterAccount(it.id))
            }
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
    }
}

@Composable
private fun TypeFilter(
    filterState: FilterState,
    onSelected: (AccountUiModel) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        //
        filterState.selectedAccounts.firstOrNull()?.let { account ->
            AccountChipWithDropdown(
                initialAcc = account,
                accounts = filterState.allAccounts,
                onSelected = onSelected,
                iconName = account.storedIcon.name,
            )
        }
        if (!filterState.todayIncluded) {
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
}
