package com.naveenapps.expensemanager.feature.category.selection

import Notify
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
import com.naveenapps.expensemanager.core.model.Category
import com.naveenapps.expensemanager.feature.category.list.CategoryCheckedItem
import com.naveenapps.expensemanager.feature.category.list.getRandomCategoryData
import expensemanager.feature.category4mp.generated.resources.Res
import expensemanager.feature.category4mp.generated.resources.category_selection_message
import expensemanager.feature.category4mp.generated.resources.clear_all
import expensemanager.feature.category4mp.generated.resources.select
import expensemanager.feature.category4mp.generated.resources.select_account
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MultipleCategoriesSelectionScreen(
    viewModel: CategorySelectionViewModel = koinViewModel(),
    selectedCategories: List<Category> = emptyList(),
    onItemSelection: ((List<Category>, Boolean) -> Unit)? = null,
) {
    viewModel.selectAllThisCategory(selectedCategories)

    CategorySelectionView(viewModel, onItemSelection)
}

@Composable
private fun CategorySelectionView(
    viewModel: CategorySelectionViewModel,
    onItemSelection: ((List<Category>, Boolean) -> Unit)?,
) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategories by viewModel.selectedCategories.collectAsState()
    val msg = stringResource(Res.string.category_selection_message)

    MultipleCategoriesSelectionScreen(
        modifier = Modifier,
        categories = categories,
        selectedCategories = selectedCategories,
        onApplyChanges = {
            if (selectedCategories.isNotEmpty()) {
                onItemSelection?.invoke(
                    selectedCategories,
                    selectedCategories.size == categories.size,
                )
            } else {
                Notify(message = msg)
            }
        },
        onClearChanges = viewModel::clearChanges,
        onItemSelection = viewModel::selectThisCategory,
    )
}

@Composable
fun MultipleCategoriesSelectionScreen(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedCategories: List<Category>,
    onApplyChanges: (() -> Unit),
    onClearChanges: (() -> Unit),
    onItemSelection: ((Category, Boolean) -> Unit)? = null,
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
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            )
        }
        items(categories, key = { it.id }) { category ->
            val isSelected = selectedCategories.fastAny { it.id == category.id }
            CategoryCheckedItem(
                modifier = Modifier
                    .clickable {
                        onItemSelection?.invoke(category, isSelected.not())
                    }
                    .padding(start = 16.dp, end = 16.dp),
                name = category.name,
                icon = category.storedIcon.name,
                iconBackgroundColor = category.storedIcon.backgroundColor,
                isSelected = isSelected,
                onCheckedChange = {
                    onItemSelection?.invoke(category, it)
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
private fun MultipleCategoriesSelectionScreenPreview() {
    ExpenseManagerTheme {
        MultipleCategoriesSelectionScreen(
            categories = getRandomCategoryData(),
            selectedCategories = getRandomCategoryData(),
            onApplyChanges = {},
            onClearChanges = {},
            onItemSelection = null,
        )
    }
}
