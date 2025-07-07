package com.naveenapps.expensemanager.core.designsystem4mp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem4mp.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem4mp.ui.utils.IconSpecModifier
import com.naveenapps.expensemanager.core.designsystem4mp.ui.utils.SmallIconSpecModifier
import com.naveenapps.expensemanager.core.designsystem4mp.ui.utils.toColor
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.account_balance_wallet
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconAndBackgroundView(
    icon: DrawableResource,
    iconBackgroundColor: String,
    modifier: Modifier = Modifier,
    name: String? = null,
) {
    IconView(
        modifier.then(IconSpecModifier),
        iconBackgroundColor,
        icon,
        name,
        18.dp,
    )
}

@Composable
fun SmallIconAndBackgroundView(
    icon: DrawableResource,
    iconBackgroundColor: String,
    modifier: Modifier = Modifier,
    name: String? = null,
    iconSize: Dp = 12.dp,
) {
    IconView(
        modifier.then(SmallIconSpecModifier),
        iconBackgroundColor = iconBackgroundColor,
        icon = icon,
        name = name,
        iconSize = iconSize,
    )
}

@Composable
private fun IconView(
    modifier: Modifier,
    iconBackgroundColor: String,
    icon: DrawableResource,
    name: String?,
    iconSize: Dp = 18.dp,
) {
    Box(modifier = modifier) {
        RoundIconView(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            iconBackgroundColor = iconBackgroundColor,
        )
        Image(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            imageVector = vectorResource(resource = icon),
            colorFilter = ColorFilter.tint(color = Color.White),
            contentDescription = name,
        )
    }
}

@Composable
fun RoundIconView(
    iconBackgroundColor: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            onDraw = {
                drawCircle(color = iconBackgroundColor.toColor())
            },
        )
    }
}

@Preview
@Composable
fun IconAndBackgroundViewPreview() {
    ExpenseManagerTheme {
        Column {
            IconAndBackgroundView(
                icon = Res.drawable.account_balance_wallet,
                iconBackgroundColor = "#000000",
            )
            SmallIconAndBackgroundView(
                icon = Res.drawable.account_balance_wallet,
                iconBackgroundColor = "#000000",
                iconSize = 12.dp,
            )
        }
    }
}
