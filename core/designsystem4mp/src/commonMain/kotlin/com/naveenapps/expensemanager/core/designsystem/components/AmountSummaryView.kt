package com.naveenapps.expensemanager.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.GREEN_500
import com.naveenapps.expensemanager.core.common.utils.RED_500
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.expense
import expensemanager.core.designsystem4mp.generated.resources.income
import expensemanager.core.designsystem4mp.generated.resources.total
import expensemanager.core.designsystem4mp.generated.resources.transaction_summary
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColorIconAmountView(
    color: Int?,
    icon: ImageVector?,
    amount: String,
    modifier: Modifier = Modifier,
    title: String,
) {
    Row(modifier = modifier) {
        if (icon != null && color != null) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(
                        color = Color(color),
                        shape = CircleShape,
                    )
                    .size(16.dp)
                    .padding(2.dp)
                    .align(Alignment.CenterVertically),
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
            )
        } else {
            Spacer(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(16.dp)
                    .padding(2.dp)
                    .align(Alignment.CenterVertically),
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = amount,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
            color = if (color != null) {
                Color(color)
            } else {
                Color.Unspecified
            },
        )
    }
}

@Composable
fun AmountInfoWidget(
    expenseAmount: String,
    incomeAmount: String,
    balanceAmount: String,
    transactionPeriod: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            WidgetHeader(
                title = stringResource(resource = Res.string.transaction_summary),
                subTitle = transactionPeriod,
            )

            ColorIconAmountView(
                color = RED_500,
                icon = Icons.Default.ArrowUpward,
                amount = expenseAmount,
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.expense),
            )
            ColorIconAmountView(
                color = GREEN_500,
                icon = Icons.Default.ArrowDownward,
                amount = incomeAmount,
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.income),
            )
            HorizontalDivider()
            ColorIconAmountView(
                color = null,
                icon = null,
                amount = balanceAmount,
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(resource = Res.string.total),
            )
        }
    }
}

@Composable
fun WidgetHeader(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterVertically),
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 4.dp, end = 4.dp),
            text = "-",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = subTitle,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun AmountViewPreview() {
    val amountValue = "10000000.0$"

    ExpenseManagerTheme {
        AmountInfoWidget(
            expenseAmount = amountValue,
            incomeAmount = amountValue,
            balanceAmount = amountValue,
            transactionPeriod = "This Month(Oct 2023)",
        )
    }
}
