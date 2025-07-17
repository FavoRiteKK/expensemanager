package com.naveenapps.expensemanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.naveenapps.expensemanager.core.common.di.dispatcherModule
import com.naveenapps.expensemanager.core.data.di.repositoryModule
import com.naveenapps.expensemanager.core.datastore.di.dataStoreModule
import com.naveenapps.expensemanager.core.designsystem.utils.shouldUseDarkTheme
import com.naveenapps.expensemanager.core.domain.usecase.GetOnboardingStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.theme.GetCurrentThemeUseCase
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import com.naveenapps.expensemanager.core.repository.AppComponentProvider
import com.naveenapps.expensemanager.ui.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val appModule = module {
    factoryOf(::GetCurrentThemeUseCase)
    factoryOf(::GetOnboardingStatusUseCase)
    viewModelOf(::AppViewModel)
}

private val appDeclaration: KoinAppDeclaration = {
    modules(dispatcherModule)
    modules(dataStoreModule)
    modules(repositoryModule + appModule)
}

@Composable
fun koinApp() = KoinApplication(application = appDeclaration) {
//    val v: ThemeRepository = koinInject()
//    val v: GetCurrentThemeUseCase = koinInject()
    val v: AppViewModel = koinViewModel()
//    App()
}

@Composable
@Preview
fun App(
    viewModel: AppViewModel = koinViewModel(),
    appComposeNavigator: AppComposeNavigator = koinInject(),
    appComponentProvider: AppComponentProvider = koinInject(),
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val onBoardingStatus by viewModel.onboardingStatus.collectAsState()
    val isDarkTheme = shouldUseDarkTheme(theme = currentTheme.mode)

    if (onBoardingStatus != null) {
        MainScreen(
            appComposeNavigator,
            appComponentProvider,
            isDarkTheme,
            if (onBoardingStatus == true) {
                ExpenseManagerScreens.Home
            } else {
                ExpenseManagerScreens.IntroScreen
            }
        )
    }
}
