package com.naveenapps.expensemanager.core.domain.usecase.account

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.AccountRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_ACCOUNT
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@UsesMocks(AccountRepository::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FindAccountByIdUseCaseTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val accountRepository: AccountRepository by lazyOf(mocker.mock())

    private lateinit var findAccountByIdUseCase: FindAccountByIdUseCase

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        findAccountByIdUseCase = FindAccountByIdUseCase(accountRepository)
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
    }

    @Test
    fun whenAccountIsValidShouldReturnValidAccountSuccessfully() = runTest {
        mocker.everySuspending {
            accountRepository.findAccount(FAKE_ACCOUNT.id)
        } returns Resource.Success(FAKE_ACCOUNT)

        val response = findAccountByIdUseCase.invoke(FAKE_ACCOUNT.id)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isEqualTo(FAKE_ACCOUNT)
    }

    @Test
    fun whenAccountIdIsInEmptyShouldReturnError() = runTest {
        val response = findAccountByIdUseCase.invoke("")
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenAccountIdIsInNullShouldReturnError() = runTest {
        val response = findAccountByIdUseCase.invoke(null)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }
}
