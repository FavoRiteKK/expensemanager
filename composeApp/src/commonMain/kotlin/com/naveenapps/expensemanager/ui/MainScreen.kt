package com.naveenapps.expensemanager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.naveenapps.expensemanager.core.designsystem4mp.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.navigation4mp.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation4mp.ExpenseManagerScreens
import com.naveenapps.expensemanager.core.repository4mp.ActivityComponentProvider

@Composable
fun MainScreen(
    composeNavigator: AppComposeNavigator,
    componentProvider: ActivityComponentProvider,
    isDarkTheme: Boolean,
    landingScreen: ExpenseManagerScreens,
) {
    ExpenseManagerTheme(isDarkTheme = isDarkTheme) {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        HomePageNavHostContainer(
            componentProvider,
            navHostController,
            landingScreen
        )
    }
}
