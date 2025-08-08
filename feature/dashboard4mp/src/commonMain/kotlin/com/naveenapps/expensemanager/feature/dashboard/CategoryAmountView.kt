package com.naveenapps.expensemanager.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.components.DashboardWidgetTitle
import com.naveenapps.expensemanager.core.designsystem.ui.components.PieChartUiData
import com.naveenapps.expensemanager.core.designsystem.ui.components.PieChartView
import com.naveenapps.expensemanager.core.designsystem.ui.utils.toColorInt
import com.naveenapps.expensemanager.core.model.CategoryTransactionState
import com.naveenapps.expensemanager.feature.category.transaction.CategoryTransactionSmallItem
import expensemanager.feature.dashboard4mp.generated.resources.Res
import expensemanager.feature.dashboard4mp.generated.resources.categories
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategoryAmountView(
    modifier: Modifier = Modifier,
    categoryTransactionState: CategoryTransactionState,
) {
    Column(modifier = modifier) {
        DashboardWidgetTitle(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.categories),
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            PieChartView(
                totalAmountText = categoryTransactionState.totalAmount.amountString ?: "",
                chartData = categoryTransactionState.pieChartData.map {
                    PieChartUiData(
                        it.name,
                        it.value,
                        it.color.toColorInt(),
                    )
                },
            )
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                repeat(categoryTransactionState.categoryTransactions.size) {
                    if (it < 4) {
                        val item = categoryTransactionState.categoryTransactions[it]
                        CategoryTransactionSmallItem(
                            name = item.category.name,
                            icon = item.category.storedIcon.name,
                            iconBackgroundColor = item.category.storedIcon.backgroundColor,
                            amount = item.amount.amountString ?: "",
                        )
                    }
                }
            }
        }
    }
}

//@AppPreviewsLightAndDarkMode
//@Composable
//fun CategoryAmountViewPreview() {
//    ExpenseManagerTheme {
//        Surface {
//            CategoryAmountView(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                categoryTransactionState = getRandomCategoryTransactionData(),
//            )
//        }
//    }
//}
