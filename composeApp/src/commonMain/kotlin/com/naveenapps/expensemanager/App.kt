package com.naveenapps.expensemanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.naveenapps.expensemanager.core.designsystem.utils.shouldUseDarkTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel()) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val onBoardingStatus by viewModel.onboardingStatus.collectAsState()
    val isDarkTheme = shouldUseDarkTheme(theme = currentTheme.mode)
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = isDarkTheme.not()

    if (onBoardingStatus != null) {
        MainScreen(
            appComposeNavigator,
            activityComponentProvider,
            isDarkTheme,
            if (onBoardingStatus == true) {
                ExpenseManagerScreens.Home
            } else {
                ExpenseManagerScreens.IntroScreen
            }
        )
    }
}