package com.naveenapps.expensemanager.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.ui.utils.getBalanceBGColor
import com.naveenapps.expensemanager.core.designsystem.ui.utils.getExpenseBGColor
import com.naveenapps.expensemanager.core.designsystem.ui.utils.getIncomeBGColor
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.balance
import expensemanager.core.designsystem4mp.generated.resources.expense
import expensemanager.core.designsystem4mp.generated.resources.income
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AmountStatusView(
    modifier: Modifier,
    expenseAmount: String,
    incomeAmount: String,
    balanceAmount: String,
    showBalance: Boolean = false,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        NewColorIconAmountView(
            amount = incomeAmount,
            title = stringResource(resource = Res.string.income),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            showBalance = showBalance,
            color = getIncomeBGColor(),
        )
        NewColorIconAmountView(
            amount = expenseAmount,
            title = stringResource(resource = Res.string.expense),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            showBalance = showBalance,
            color = getExpenseBGColor(),
        )
        if (showBalance) {
            NewColorIconAmountView(
                amount = balanceAmount,
                title = stringResource(resource = Res.string.balance),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                showBalance = showBalance,
                color = getBalanceBGColor(),
            )
        }
    }
}

@Composable
fun NewColorIconAmountView(
    amount: String,
    title: String,
    modifier: Modifier = Modifier,
    showBalance: Boolean = false,
    color: Color,
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                style = if (showBalance) {
                    MaterialTheme.typography.bodySmall
                } else {
                    MaterialTheme.typography.titleMedium
                },
            )
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                text = amount,
                fontWeight = FontWeight.Bold,
                style = if (showBalance) {
                    MaterialTheme.typography.bodySmall
                } else {
                    MaterialTheme.typography.headlineSmall
                },
            )
        }
    }
}

@Preview
@Composable
private fun AmountStatusViewPreview() {
    val amount = "10000000.0$"
    ExpenseManagerTheme {
        Column {
            AmountStatusView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                expenseAmount = amount,
                incomeAmount = amount,
                balanceAmount = amount,
            )
            AmountStatusView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                expenseAmount = amount,
                incomeAmount = amount,
                balanceAmount = amount,
                showBalance = true,
            )
        }
    }
}
