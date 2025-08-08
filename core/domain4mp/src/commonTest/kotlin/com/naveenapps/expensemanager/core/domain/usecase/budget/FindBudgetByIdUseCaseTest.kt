package com.naveenapps.expensemanager.core.domain.usecase.budget

import com.naveenapps.expensemanager.core.model.Resource
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
class FindBudgetByIdUseCaseTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val budgetRepository: BudgetRepository by lazyOf(mocker.mock())

    private lateinit var findBudgetByIdUseCase: FindBudgetByIdUseCase

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        findBudgetByIdUseCase = FindBudgetByIdUseCase(budgetRepository)
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
    }

    @Test
    fun whenBudgetIsValidShouldReturnValidBudgetSuccessfully() = runTest {
        mocker.everySuspending {
            budgetRepository.findBudgetById(FAKE_BUDGET.id)
        } returns Resource.Success(FAKE_BUDGET)

        val response = findBudgetByIdUseCase.invoke(FAKE_BUDGET.id)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isEqualTo(FAKE_BUDGET)
    }

    @Test
    fun whenBudgetIdIsInEmptyShouldReturnError() = runTest {
        val response = findBudgetByIdUseCase.invoke("")
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenBudgetIdIsInNullShouldReturnError() = runTest {
        val response = findBudgetByIdUseCase.invoke(null)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }
}
