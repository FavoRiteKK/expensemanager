package com.naveenapps.expensemanager.core.domain.usecase.budget

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.core.repository.BudgetRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_BUDGET
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@UsesMocks(BudgetRepository::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AddBudgetUseCaseTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val budgetRepository: BudgetRepository by lazyOf(mocker.mock())
    private val checkBudgetValidateUseCase = CheckBudgetValidateUseCase()
    private lateinit var addBudgetUseCase: AddBudgetUseCase

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        addBudgetUseCase = AddBudgetUseCase(
            budgetRepository,
            checkBudgetValidateUseCase,
        )
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
    }

    @Test
    fun whenBudgetIsValidShouldAddSuccessfully() = runTest {
        mocker.everySuspending {
            budgetRepository.addBudget(FAKE_BUDGET)
        } returns Resource.Success(true)

        val response = addBudgetUseCase.invoke(FAKE_BUDGET)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun whenBudgetIsInValidShouldReturnError() = runTest {
        val response = addBudgetUseCase.invoke(FAKE_BUDGET.copy(id = ""))
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetNameIsInValidShouldReturnError() = runTest {
        val response = addBudgetUseCase.invoke(FAKE_BUDGET.copy(name = ""))
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetStoredIconNameIsInValidShouldReturnError() = runTest {
        val response =
            addBudgetUseCase.invoke(FAKE_BUDGET.copy(storedIcon = StoredIcon("", "#ffffff")))
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetStoredIconBGColorIsInValidShouldReturnError() = runTest {
        val response =
            addBudgetUseCase.invoke(FAKE_BUDGET.copy(storedIcon = StoredIcon("ic_calendar", "")))
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetStoredIconBGColorIsInValidColorShouldReturnError() = runTest {
        val response =
            addBudgetUseCase.invoke(
                FAKE_BUDGET.copy(
                    storedIcon = StoredIcon(
                        "ic_calendar",
                        "sample",
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
    fun whenBudgetAccountsNotAvailableShouldReturnError() = runTest {
        val response = addBudgetUseCase.invoke(
            FAKE_BUDGET.copy(
                isAllAccountsSelected = false,
                accounts = emptyList(),
            ),
        )
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetCategoriesNotAvailableShouldReturnError() = runTest {
        val response = addBudgetUseCase.invoke(
            FAKE_BUDGET.copy(
                isAllCategoriesSelected = false,
                categories = emptyList(),
            ),
        )
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }
}