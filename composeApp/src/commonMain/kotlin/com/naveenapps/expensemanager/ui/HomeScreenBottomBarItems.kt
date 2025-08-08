package com.naveenapps.expensemanager.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import expensemanager.composeapp.generated.resources.Res
import expensemanager.composeapp.generated.resources.analysis
import expensemanager.composeapp.generated.resources.categories
import expensemanager.composeapp.generated.resources.ic_category
import expensemanager.composeapp.generated.resources.ic_chart
import expensemanager.composeapp.generated.resources.ic_home
import expensemanager.composeapp.generated.resources.ic_transfer
import expensemanager.composeapp.generated.resources.title_home
import expensemanager.composeapp.generated.resources.transaction
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class HomeScreenBottomBarItems(
    @StringRes val labelResourceID: StringResource,
    @DrawableRes val iconResourceID: DrawableResource,
) {
    Home(Res.string.title_home, Res.drawable.ic_home),
    Analysis(Res.string.analysis, Res.drawable.ic_chart),
    Transaction(Res.string.transaction, Res.drawable.ic_transfer),
    Category(Res.string.categories, Res.drawable.ic_category),
}
