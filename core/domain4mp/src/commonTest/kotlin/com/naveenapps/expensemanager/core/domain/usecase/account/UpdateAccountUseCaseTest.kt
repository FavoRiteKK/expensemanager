package com.naveenapps.expensemanager.core.domain.usecase.account

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.StoredIcon
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
class UpdateAccountUseCaseTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val accountRepository: AccountRepository by lazyOf(mocker.mock())
    private val checkAccountValidationUseCase = CheckAccountValidationUseCase()

    private lateinit var updateAccountUseCase: UpdateAccountUseCase

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        updateAccountUseCase = UpdateAccountUseCase(
            accountRepository,
            checkAccountValidationUseCase,
        )
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
    }

    @Test
    fun whenAccountIsValidShouldUpdateSuccessfully() = runTest {
        mocker.everySuspending {
            accountRepository.updateAccount(FAKE_ACCOUNT)
        } returns Resource.Success(true)

        val response = updateAccountUseCase.invoke(FAKE_ACCOUNT)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun whenAccountIsInValidShouldReturnError() = runTest {
        val response = updateAccountUseCase.invoke(FAKE_ACCOUNT.copy(id = ""))
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenAccountNameIsInValidShouldReturnError() = runTest {
        val response = updateAccountUseCase.invoke(FAKE_ACCOUNT.copy(name = ""))
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenAccountStoredIconNameIsInValidShouldReturnError() = runTest {
        val response = updateAccountUseCase.invoke(
            FAKE_ACCOUNT.copy(storedIcon = StoredIcon("", "#ffffff")),
        )
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenAccountStoredIconBGColorIsInValidShouldReturnError() = runTest {
        val response = updateAccountUseCase.invoke(
            FAKE_ACCOUNT.copy(
                storedIcon = StoredIcon(
                    "ic_calendar",
                    "",
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
    fun whenAccountStoredIconBGColorIsInValidColorShouldReturnError() = runTest {
        val response = updateAccountUseCase.invoke(
            FAKE_ACCOUNT.copy(
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
}