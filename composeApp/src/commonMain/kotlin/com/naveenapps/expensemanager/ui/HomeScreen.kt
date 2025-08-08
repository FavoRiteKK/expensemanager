package com.naveenapps.expensemanager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import com.naveenapps.expensemanager.core.repository.AppComponentProvider
import com.naveenapps.expensemanager.feature.about.AboutScreen
import com.naveenapps.expensemanager.feature.account.create.AccountCreateScreen
import com.naveenapps.expensemanager.feature.account.list.AccountListScreen
import com.naveenapps.expensemanager.feature.account.reorder.AccountReOrderScreen
import com.naveenapps.expensemanager.feature.analysis.AnalysisScreen
import com.naveenapps.expensemanager.feature.budget.create.BudgetCreateScreen
import com.naveenapps.expensemanager.feature.budget.details.BudgetDetailScreen
import com.naveenapps.expensemanager.feature.budget.list.BudgetListScreen
import com.naveenapps.expensemanager.feature.category.create.CategoryCreateScreen
import com.naveenapps.expensemanager.feature.category.details.CategoryDetailScreen
import com.naveenapps.expensemanager.feature.category.list.CategoryListScreen
import com.naveenapps.expensemanager.feature.category.transaction.CategoryTransactionTabScreen
import com.naveenapps.expensemanager.feature.currency.CurrencyCustomiseScreen
import com.naveenapps.expensemanager.feature.dashboard.DashboardScreen
import com.naveenapps.expensemanager.feature.export.ExportScreen
import com.naveenapps.expensemanager.feature.onboarding.OnboardingScreen
import com.naveenapps.expensemanager.feature.onboarding.into.IntroScreen
import com.naveenapps.expensemanager.feature.reminder.ReminderScreen
import com.naveenapps.expensemanager.feature.settings.SettingsScreen
import com.naveenapps.expensemanager.feature.settings.advanced.AdvancedSettingsScreen
import com.naveenapps.expensemanager.feature.transaction.create.TransactionCreateScreen
import com.naveenapps.expensemanager.feature.transaction.list.TransactionListScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePageNavHostContainer(
    backupRepository: AppComponentProvider,
    navHostController: NavHostController,
    landingScreen: ExpenseManagerScreens,
) {
    NavHost(
        navController = navHostController,
        startDestination = landingScreen,
    ) {
        this.expenseManagerNavigation(backupRepository)
    }
}

fun NavGraphBuilder.expenseManagerNavigation(
    componentProvider: AppComponentProvider,
) {
    composable<ExpenseManagerScreens.IntroScreen> {
        IntroScreen(componentProvider.getShareRepository())
    }
    composable<ExpenseManagerScreens.Onboarding> {
        OnboardingScreen()
    }
    composable<ExpenseManagerScreens.Home> {
        HomeScreen()
    }
    composable<ExpenseManagerScreens.CategoryList> {
        CategoryListScreen()
    }
    composable<ExpenseManagerScreens.CategoryCreate> {
        CategoryCreateScreen()
    }
    composable<ExpenseManagerScreens.CategoryDetails> {
        CategoryDetailScreen()
    }
    composable<ExpenseManagerScreens.TransactionList> {
        TransactionListScreen(showBackNavigationIcon = true)
    }
    composable<ExpenseManagerScreens.TransactionCreate> {
        TransactionCreateScreen()
    }
    composable<ExpenseManagerScreens.AccountList> {
        AccountListScreen()
    }
    composable<ExpenseManagerScreens.AccountCreate> {
        AccountCreateScreen()
    }
    composable<ExpenseManagerScreens.BudgetList> {
        BudgetListScreen()
    }
    composable<ExpenseManagerScreens.BudgetCreate> {
        BudgetCreateScreen()
    }
    composable<ExpenseManagerScreens.BudgetDetails> {
        BudgetDetailScreen()
    }
    composable<ExpenseManagerScreens.AnalysisScreen> {
        AnalysisScreen()
    }
    composable<ExpenseManagerScreens.Settings> {
        SettingsScreen(componentProvider.getShareRepository())
    }
    composable<ExpenseManagerScreens.ExportScreen> {
        ExportScreen()
    }
    composable<ExpenseManagerScreens.ReminderScreen> {
        ReminderScreen()
    }
    composable<ExpenseManagerScreens.CurrencyCustomiseScreen> {
        CurrencyCustomiseScreen()
    }
    composable<ExpenseManagerScreens.CategoryTransaction> {
        CategoryTransactionTabScreen()
    }
    composable<ExpenseManagerScreens.AboutUsScreen> {
        AboutScreen(componentProvider.getShareRepository())
    }
    composable<ExpenseManagerScreens.AdvancedSettingsScreen> {
        AdvancedSettingsScreen()
    }
    composable<ExpenseManagerScreens.AccountReOrderScreen> {
        AccountReOrderScreen()
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val homeScreenBottomBarItems by viewModel.homeScreenBottomBarItems.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                HomeScreenBottomBarItems.entries.forEach { uiSystem ->
                    NavigationBarItem(
                        selected = homeScreenBottomBarItems == uiSystem,
                        onClick = { viewModel.setUISystem(uiSystem) },
                        icon = {
                            Icon(
                                painterResource(uiSystem.iconResourceID),
                                stringResource(uiSystem.labelResourceID),
                            )
                        },
                        label = { Text(stringResource(uiSystem.labelResourceID)) },
                    )
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(
                bottom = paddingValues.calculateBottomPadding(),
            ),
        ) {
            when (homeScreenBottomBarItems) {
                HomeScreenBottomBarItems.Home -> {
                    DashboardScreen()
                }

                HomeScreenBottomBarItems.Analysis -> {
                    AnalysisScreen()
                }

                HomeScreenBottomBarItems.Transaction -> {
                    TransactionListScreen()
                }

                HomeScreenBottomBarItems.Category -> {
                    CategoryTransactionTabScreen()
                }
            }
        }
    }
}
