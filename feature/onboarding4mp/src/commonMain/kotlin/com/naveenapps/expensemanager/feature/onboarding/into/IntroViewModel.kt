@file:OptIn(ExperimentalTime::class)

package com.naveenapps.expensemanager.feature.onboarding.into

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.domain.usecase.account.AddAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.category.AddCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.GetPreloadStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.onboarding.SetPreloadStatusUseCase
import com.naveenapps.expensemanager.core.model.Account
import com.naveenapps.expensemanager.core.model.AccountType
import com.naveenapps.expensemanager.core.model.Category
import com.naveenapps.expensemanager.core.model.CategoryType
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private val BASE_CATEGORY_LIST = listOf(
    Category(
        id = "1",
        name = "Clothing",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "apparel",
            backgroundColor = "#F44336",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "2",
        name = "Entertainment",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "sports_esports",
            backgroundColor = "#E91E63",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "3",
        name = "Food",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "restaurant",
            backgroundColor = "#9C27B0",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "4",
        name = "Health",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "home_health",
            backgroundColor = "#673AB7",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "5",
        name = "Leisure",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "flights_and_hotels",
            backgroundColor = "#3F51B5",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "6",
        name = "Shopping",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "shopping_cart",
            backgroundColor = "#2196F3",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "7",
        name = "Transportation",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "travel",
            backgroundColor = "#03A9F4",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "8",
        name = "Utilities",
        type = CategoryType.EXPENSE,
        storedIcon = StoredIcon(
            name = "other_admission",
            backgroundColor = "#00BCD4",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "9",
        name = "Salary",
        type = CategoryType.INCOME,
        storedIcon = StoredIcon(
            name = "savings",
            backgroundColor = "#4CAF50",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "10",
        name = "Gift",
        type = CategoryType.INCOME,
        storedIcon = StoredIcon(
            name = "featured_seasonal_and_gifts",
            backgroundColor = "#E65100",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
    Category(
        id = "11",
        name = "Coupons",
        type = CategoryType.INCOME,
        storedIcon = StoredIcon(
            name = "redeem",
            backgroundColor = "#3E2723",
        ),
        createdOn = Clock.System.now().asCurrentDateTime(),
        updatedOn = Clock.System.now().asCurrentDateTime(),
    ),
)

private val BASE_ACCOUNT_LIST = listOf(
    Account(
        "1",
        "Cash",
        AccountType.REGULAR,
        storedIcon = StoredIcon(
            name = "savings",
            backgroundColor = "#4CAF50",
        ),
        Clock.System.now().asCurrentDateTime(),
        Clock.System.now().asCurrentDateTime(),
    ),
    Account(
        "2",
        "Card-xxx",
        AccountType.CREDIT,
        storedIcon = StoredIcon(
            name = "credit_card",
            backgroundColor = "#4CAF50",
        ),
        Clock.System.now().asCurrentDateTime(),
        Clock.System.now().asCurrentDateTime(),
    ),
    Account(
        "3",
        "Bank Account",
        AccountType.REGULAR,
        storedIcon = StoredIcon(
            name = "account_balance",
            backgroundColor = "#4CAF50",
        ),
        Clock.System.now().asCurrentDateTime(),
        Clock.System.now().asCurrentDateTime(),
    ),
)

class IntroViewModel(
    private val getPreloadStatusUseCase: GetPreloadStatusUseCase,
    private val setPreloadStatusUseCase: SetPreloadStatusUseCase,
    private val addAccountUseCase: AddAccountUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val appComposeNavigator: AppComposeNavigator
) : ViewModel() {

    private suspend fun preloadDatabase() {
        val isPreloaded = getPreloadStatusUseCase.invoke()

        if (isPreloaded.not()) {
            BASE_CATEGORY_LIST.forEach { value ->
                addCategoryUseCase.invoke(value)
            }

            BASE_ACCOUNT_LIST.forEach { value ->
                addAccountUseCase.invoke(value)
            }

            setPreloadStatusUseCase.invoke(true)
        }
    }

    fun navigate() {
        viewModelScope.launch {
            preloadDatabase()
            appComposeNavigator.navigate(ExpenseManagerScreens.Onboarding)
        }
    }
}