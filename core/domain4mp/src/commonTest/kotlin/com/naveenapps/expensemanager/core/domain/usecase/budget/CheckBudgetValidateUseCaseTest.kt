package com.naveenapps.expensemanager.core.domain.usecase.budget

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.core.testing.FAKE_BUDGET
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlin.test.Test

class CheckBudgetValidateUseCaseTest {

    private var checkBudgetValidateUseCase = CheckBudgetValidateUseCase()

    @Test
    fun whenProperBudgetShouldReturnSuccess() {
        val response = checkBudgetValidateUseCase.invoke(FAKE_BUDGET)

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        val success = (response as Resource.Success)
        LWTruth_assertThat(success).isNotNull()
        LWTruth_assertThat(success.data).isTrue()
    }

    @Test
    fun whenBudgetIdIsNotAvailableShouldResultError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(id = ""),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenStoredIconNameNotAvailableShouldResultError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(
                storedIcon = StoredIcon(
                    name = "",
                    backgroundColor = "#ffff",
                ),
            ),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenStoredIconBGColorNotAvailableShouldResultError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(
                storedIcon = StoredIcon(
                    name = "Sample",
                    backgroundColor = "",
                ),
            ),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenStoredIconBGColorNotStartsWithHashShouldResultError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(
                storedIcon = StoredIcon(
                    name = "Sample",
                    backgroundColor = "sample",
                ),
            ),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetNameIsNotAvailableShouldResultError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(name = ""),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetsEmptyForShouldReturnError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(isAllAccountsSelected = false, accounts = emptyList()),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenCategoriesEmptyForShouldReturnError() {
        val response = checkBudgetValidateUseCase.invoke(
            FAKE_BUDGET.copy(isAllCategoriesSelected = false, categories = emptyList()),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }
}
