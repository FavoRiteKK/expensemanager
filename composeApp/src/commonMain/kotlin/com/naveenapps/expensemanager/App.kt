package com.naveenapps.expensemanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.naveenapps.expensemanager.core.common.di.dispatcher
import com.naveenapps.expensemanager.core.data.di.component
import com.naveenapps.expensemanager.core.data.di.repository
import com.naveenapps.expensemanager.core.database.di.database
import com.naveenapps.expensemanager.core.datastore.di.dataStore
import com.naveenapps.expensemanager.core.designsystem.components.IconSelectionViewModel
import com.naveenapps.expensemanager.core.designsystem.utils.shouldUseDarkTheme
import com.naveenapps.expensemanager.core.domain.usecase.account.AddAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.account.CheckAccountValidationUseCase
import com.naveenapps.expensemanager.core.domain.usecase.account.DeleteAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.account.FindAccountByIdUseCase
import com.naveenapps.expensemanager.core.domain.usecase.account.GetAllAccountsUseCase
import com.naveenapps.expensemanager.core.domain.usecase.account.UpdateAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.account.UpdateAllAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.AddBudgetUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.CheckBudgetValidateUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.DeleteBudgetUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.FindBudgetByIdUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.GetBudgetDetailUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.GetBudgetTransactionsUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.GetBudgetsUseCase
import com.naveenapps.expensemanager.core.domain.usecase.budget.UpdateBudgetUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.AddCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.CheckCategoryValidationUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.DeleteCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.FindCategoryByIdFlowUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.FindCategoryByIdUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.GetAllCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.UpdateCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.country.GetCountriesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetCurrencyUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetDefaultCurrencyUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetFormattedAmountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.SaveCurrencyUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.export.ExportFileUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.account.GetSelectedAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.account.UpdateSelectedAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.category.GetSelectedCategoriesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.category.UpdateSelectedCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetAllDateRangeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeByTypeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetTransactionGroupTypeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.MoveDateRangeBackwardUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.MoveDateRangeForwardUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.SaveDateRangeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.SetDateRangesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.transactiontype.GetSelectedTransactionTypesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.transactiontype.UpdateSelectedTransactionTypesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.GetOnboardingStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.GetPreloadStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.SetOnboardingStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.SetPreloadStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.GetReminderStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.GetReminderTimeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.SaveReminderTimeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.UpdateReminderStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.theme.ApplyThemeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.theme.GetCurrentThemeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.theme.GetThemesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.theme.SaveThemeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.AddTransactionUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.DeleteTransactionUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.FindTransactionByIdUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetAmountStateUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetAverageDataUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetChartDataUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetExpenseAmountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetExportTransactionsUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetIncomeAmountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetTransactionGroupByCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetTransactionWithFilterUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.UpdateTransactionUseCase
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import com.naveenapps.expensemanager.core.navigation.di.navigation
import com.naveenapps.expensemanager.core.notification.NotificationScheduler
import com.naveenapps.expensemanager.core.repository.AppComponentProvider
import com.naveenapps.expensemanager.feature.about.AboutUsViewModel
import com.naveenapps.expensemanager.feature.account.create.AccountCreateViewModel
import com.naveenapps.expensemanager.feature.account.list.AccountListViewModel
import com.naveenapps.expensemanager.feature.account.reorder.AccountReOrderViewModel
import com.naveenapps.expensemanager.feature.account.selection.AccountSelectionViewModel
import com.naveenapps.expensemanager.feature.analysis.AnalysisScreenViewModel
import com.naveenapps.expensemanager.feature.budget.create.BudgetCreateViewModel
import com.naveenapps.expensemanager.feature.budget.details.BudgetDetailViewModel
import com.naveenapps.expensemanager.feature.budget.list.BudgetListViewModel
import com.naveenapps.expensemanager.feature.category.create.CategoryCreateViewModel
import com.naveenapps.expensemanager.feature.category.details.CategoryDetailViewModel
import com.naveenapps.expensemanager.feature.category.list.CategoryListViewModel
import com.naveenapps.expensemanager.feature.category.selection.CategorySelectionViewModel
import com.naveenapps.expensemanager.feature.category.transaction.CategoryTransactionListViewModel
import com.naveenapps.expensemanager.feature.country.CountryListViewModel
import com.naveenapps.expensemanager.feature.currency.CurrencyViewModel
import com.naveenapps.expensemanager.feature.dashboard.DashboardViewModel
import com.naveenapps.expensemanager.feature.export.ExportViewModel
import com.naveenapps.expensemanager.feature.filter.FilterViewModel
import com.naveenapps.expensemanager.feature.filter.datefilter.DateFilterViewModel
import com.naveenapps.expensemanager.feature.filter.type.FilterTypeSelectionViewModel
import com.naveenapps.expensemanager.feature.onboarding.OnboardingViewModel
import com.naveenapps.expensemanager.feature.onboarding.into.IntroViewModel
import com.naveenapps.expensemanager.feature.reminder.ReminderTimePickerViewModel
import com.naveenapps.expensemanager.feature.reminder.ReminderViewModel
import com.naveenapps.expensemanager.feature.settings.SettingsViewModel
import com.naveenapps.expensemanager.feature.settings.advanced.AdvancedSettingsViewModel
import com.naveenapps.expensemanager.feature.theme.ThemeViewModel
import com.naveenapps.expensemanager.feature.transaction.create.TransactionCreateViewModel
import com.naveenapps.expensemanager.feature.transaction.list.TransactionListViewModel
import com.naveenapps.expensemanager.feature.transaction.numberpad.NumberPadViewModel
import com.naveenapps.expensemanager.ui.HomeViewModel
import com.naveenapps.expensemanager.ui.MainScreen
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val appModule = module {
    singleOf(::NotificationScheduler)

    factoryOf(::UpdateSelectedCategoryUseCase)
    factoryOf(::GetSelectedCategoriesUseCase)
    factoryOf(::AddAccountUseCase)
    factoryOf(::AddBudgetUseCase)
    factoryOf(::AddCategoryUseCase)
    factoryOf(::CheckAccountValidationUseCase)
    factoryOf(::CheckBudgetValidateUseCase)
    factoryOf(::CheckCategoryValidationUseCase)
    factoryOf(::DeleteAccountUseCase)
    factoryOf(::DeleteCategoryUseCase)
    factoryOf(::FindAccountByIdUseCase)
    factoryOf(::FindBudgetByIdUseCase)
    factoryOf(::FindCategoryByIdUseCase)
    factoryOf(::GetAllAccountsUseCase)
    factoryOf(::GetAllCategoryUseCase)
    factoryOf(::GetAllDateRangeUseCase)
    factoryOf(::GetAmountStateUseCase)
    factoryOf(::GetAverageDataUseCase)
    factoryOf(::GetBudgetDetailUseCase)
    factoryOf(::GetBudgetTransactionsUseCase)
    factoryOf(::GetBudgetsUseCase)
    factoryOf(::GetChartDataUseCase)
    factoryOf(::GetCountriesUseCase)
    factoryOf(::GetCurrencyUseCase)
    factoryOf(::GetCurrentThemeUseCase)
    factoryOf(::GetDateRangeByTypeUseCase)
    factoryOf(::GetDateRangeUseCase)
    factoryOf(::GetDefaultCurrencyUseCase)
    factoryOf(::GetExpenseAmountUseCase)
    factoryOf(::GetFormattedAmountUseCase)
    factoryOf(::GetIncomeAmountUseCase)
    factoryOf(::GetOnboardingStatusUseCase)
    factoryOf(::GetTransactionGroupByCategoryUseCase)
    factoryOf(::GetTransactionGroupTypeUseCase)
    factoryOf(::GetTransactionWithFilterUseCase)
    factoryOf(::MoveDateRangeBackwardUseCase)
    factoryOf(::MoveDateRangeForwardUseCase)
    factoryOf(::SaveCurrencyUseCase)
    factoryOf(::SaveDateRangeUseCase)
    factoryOf(::SetDateRangesUseCase)
    factoryOf(::SetOnboardingStatusUseCase)
    factoryOf(::UpdateAccountUseCase)
    factoryOf(::UpdateAllAccountUseCase)
    factoryOf(::UpdateBudgetUseCase)
    factoryOf(::UpdateCategoryUseCase)
    factoryOf(::UpdateReminderStatusUseCase)
    factoryOf(::GetSelectedTransactionTypesUseCase)
    factoryOf(::UpdateSelectedTransactionTypesUseCase)
    factoryOf(::GetSelectedAccountUseCase)
    factoryOf(::UpdateSelectedAccountUseCase)
    factoryOf(::AddTransactionUseCase)
    factoryOf(::DeleteTransactionUseCase)
    factoryOf(::FindTransactionByIdUseCase)
    factoryOf(::UpdateTransactionUseCase)
    factoryOf(::GetThemesUseCase)
    factoryOf(::SaveThemeUseCase)
    factoryOf(::ApplyThemeUseCase)
    factoryOf(::FindCategoryByIdFlowUseCase)
    factoryOf(::DeleteBudgetUseCase)
    factoryOf(::ExportFileUseCase)
    factoryOf(::GetExportTransactionsUseCase)
    factoryOf(::GetReminderStatusUseCase)
    factoryOf(::GetReminderTimeUseCase)
    factoryOf(::SaveReminderTimeUseCase)
    factoryOf(::GetPreloadStatusUseCase)
    factoryOf(::SetPreloadStatusUseCase)

    viewModelOf(::AccountCreateViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::AnalysisScreenViewModel)
    viewModelOf(::CategoryTransactionListViewModel)
    viewModelOf(::CountryListViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::IntroViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::TransactionListViewModel)
    viewModelOf(::TransactionCreateViewModel)
    viewModelOf(::IconSelectionViewModel)
    viewModelOf(::DateFilterViewModel)
    viewModelOf(::NumberPadViewModel)
    viewModelOf(::CategoryCreateViewModel)
    viewModelOf(::AdvancedSettingsViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CategoryListViewModel)
    viewModelOf(::CategoryDetailViewModel)
    viewModelOf(::BudgetListViewModel)
    viewModelOf(::BudgetCreateViewModel)
    viewModelOf(::BudgetDetailViewModel)
    viewModelOf(::ExportViewModel)
    viewModelOf(::ReminderTimePickerViewModel)
    viewModelOf(::ReminderViewModel)
    viewModelOf(::CurrencyViewModel)
    viewModelOf(::AboutUsViewModel)
    viewModelOf(::AccountReOrderViewModel)
    viewModelOf(::FilterTypeSelectionViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::AccountListViewModel)
    viewModelOf(::AccountSelectionViewModel)
    viewModelOf(::CategorySelectionViewModel)
}

private fun appDeclaration(declare: KoinApplication.() -> Unit): KoinAppDeclaration = {
    this.declare()

    modules(dispatcher)
    modules(dataStore)
    modules(database)
    modules(repository + navigation + component + appModule)
}

@Composable
fun koinApp(onDeclare: KoinApplication.() -> Unit = {}) =
    KoinApplication(application = appDeclaration(onDeclare)) {
        App()
    }

@Composable
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
